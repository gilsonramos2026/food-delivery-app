package com.delivery.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Estados possíveis de um pedido dentro da máquina de estados do delivery. " +
                "Fluxo permitido: RECEBIDO -> PREPARANDO -> SAIU_PARA_ENTREGA -> ENTREGUE. " +
                "Cancelamentos só são aceitos nos estados RECEBIDO ou PREPARANDO.",
        enumAsRef = true
)
public enum OrderStatus {

    @Schema(description = "Pedido acabou de ser criado pelo cliente e aguarda aprovação da cozinha")
    RECEBIDO,

    @Schema(description = "Pedido foi aceito e está sendo produzido na cozinha")
    PREPARANDO,

    @Schema(description = "Pedido foi coletado pelo entregador e está a caminho do endereço")
    SAIU_PARA_ENTREGA,

    @Schema(description = "Pedido foi entregue com sucesso ao cliente (Estado Final)")
    ENTREGUE,

    @Schema(description = "Pedido foi cancelado pelo cliente ou pelo administrador (Estado Final)")
    CANCELADO
}
