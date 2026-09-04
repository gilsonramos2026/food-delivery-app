export interface MockProduct {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
}

export interface MockCategory {
  id: number;
  name: string;
  displayOrder: number;
  products: MockProduct[];
}

export const mockCategories: MockCategory[] = [
  {
    id: 1,
    name: 'Pizzas Tradicionais',
    displayOrder: 1,
    products: [
      { id: 1, name: 'Pizza Calabresa', description: 'Molho de tomate, mozarela, calabresa e cebola.', price: 45.90, imageUrl: '/uploads/seed-calabresa.jpg' },
      { id: 2, name: 'Pizza Quatro Queijos', description: 'Mozarela, provolone, gorgonzola e catupiry.', price: 49.90, imageUrl: '/uploads/seed-quatro-queijos.jpg' }
    ]
  },
  {
    id: 2,
    name: 'Bebidas',
    displayOrder: 2,
    products: [
      { id: 4, name: 'Coca-Cola 2L', description: 'Refrigerante garrafa PET de 2 litros bem gelada.', price: 11.90, imageUrl: '/uploads/seed-coca.jpg' }
    ]
  }
];

export const mockBanners = [
  { id: 1, title: 'Combo Família com Desconto', imageUrl: '/uploads/banner-combo.jpg', displayOrder: 1 },
  { id: 2, title: 'Taxa de Entrega Grátis até 3km', imageUrl: '/uploads/banner-taxa-gratis.jpg', displayOrder: 2 }
];

export const mockCoupons = [
  { code: 'QUERO10', discountValue: 10.00, minOrderValue: 40.00 },
  { code: 'PROMO5', discountValue: 5.00, minOrderValue: 20.00 }
];
