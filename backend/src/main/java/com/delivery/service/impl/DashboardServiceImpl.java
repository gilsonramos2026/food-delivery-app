package com.delivery.service.impl;

import com.delivery.dto.response.DashboardResponseDTO;
import com.delivery.service.DashboardService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public DashboardResponseDTO getAdminDashboardData() {
        // 1. Calcula o Faturamento Total acumulado de pedidos concluidos
        BigDecimal revenue = (BigDecimal) entityManager.createQuery(
                        "SELECT COALESCE(SUM(o.total), 0) FROM Order o WHERE o.status = 'ENTREGUE'")
                .getSingleResult();

        // 2. Lucro Real: faturamento menos o custo de fabricação (Preço - Custo) dos produtos vendidos
        BigDecimal totalCost = (BigDecimal) entityManager.createQuery(
                        "SELECT COALESCE(SUM(i.quantity * i.product.costPrice), 0) " +
                                "FROM OrderItem i WHERE i.order.status = 'ENTREGUE'")
                .getSingleResult();
        BigDecimal profit = revenue.subtract(totalCost);

        // 3. Top 5 produtos mais vendidos do mês atual
        List<Object[]> queryResult = entityManager.createQuery(
                        "SELECT i.product.name, SUM(i.quantity) as totalSales " +
                                "FROM OrderItem i WHERE i.order.status = 'ENTREGUE' " +
                                "GROUP BY i.product.name ORDER BY totalSales DESC")
                .setMaxResults(5)
                .getResultList();

        List<DashboardResponseDTO.TopProductDTO> topProducts = queryResult.stream()
                .map(row -> new DashboardResponseDTO.TopProductDTO((String) row[0], (Long) row[1]))
                .toList();

        return new DashboardResponseDTO(revenue, profit, topProducts);
    }
}
