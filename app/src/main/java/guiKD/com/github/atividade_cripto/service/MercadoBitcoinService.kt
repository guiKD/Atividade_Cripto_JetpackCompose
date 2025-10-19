package guiKD.com.github.atividade_cripto.service

import guiKD.com.github.atividade_cripto.model.TickerResponse
import retrofit2.Response
import retrofit2.http.GET

interface MercadoBitcoinService {

    @GET("api/BTC/ticker/")
    suspend fun getTicker(): Response<TickerResponse>
}