package com.flemis.score.features.app.data.datasource.remote

import com.flemis.score.features.app.data.models.ProductDetailsModel
import com.flemis.score.features.app.data.models.PurchaseModel
import com.flemis.score.features.app.data.models.PurchaseResultModel
import kotlinx.coroutines.flow.SharedFlow

/**
 * Interface para gerenciar compras in-app de forma multiplataforma.
 */
interface BillingDataSource {

    /**
     * Inicializa o serviço de IAP. Deve ser chamado antes de qualquer outra operação.
     * @param onPurchaseUpdate Um callback para receber atualizações de compras (sucedidas, pendentes, falhas).
     */
    suspend fun initialize(onPurchasesUpdated: (PurchaseResultModel) -> Unit)
    /**
     * Consulta os detalhes de produtos in-app a partir de uma lista de IDs de produtos.
     * @param productIds Lista de IDs de produtos a serem consultados.
     * @return Lista de [br.com.flemis.bookishadventure.core.domain.models.ProductDetails] dos produtos encontrados.
     */
    suspend fun queryProducts(productsIds: List<String>): List<ProductDetailsModel>
    /**
     * Inicia o fluxo de compra para um produto específico.
     * @param product O [ProductDetails] do produto a ser comprado.
     */
    suspend fun makePurchase(productId: String)
    /**
     * Restaura compras anteriores para produtos não consumíveis ou assinaturas.
     * O resultado será entregue via o `onPurchaseUpdate` do initialize.
     */
    suspend fun restorePurchase()

    /**
     * Retorna um fluxo que emite atualizações de compras.
     * Útil para observar o status de compras de qualquer lugar.
     */
    val purchaseUpdates: SharedFlow<PurchaseResultModel>
    /**
     * Retorna a lista de compras ativas conhecidas.
     * Útil para verificar o entitlement(direito do usuario) inicial após o lançamento do app.
     */
    suspend fun getActivePurchases(): List<PurchaseModel>
    suspend fun dispose()
}