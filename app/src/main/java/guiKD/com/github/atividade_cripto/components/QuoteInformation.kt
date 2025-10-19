package guiKD.com.github.atividade_cripto.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import guiKD.com.github.atividade_cripto.components.RefreshButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun QuoteInformation() {
    val scope = rememberCoroutineScope()
    var lastValue by remember { mutableStateOf("R$ 0,00") }
    var lastDate by remember { mutableStateOf("--/--/---- --:--:--") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Cotação - BITCOIN", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = lastValue, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = lastDate)
        Spacer(modifier = Modifier.height(16.dp))

        RefreshButton(
            onClick = {
                scope.launch {
                    try {
                        val result = withContext(Dispatchers.IO) {
                            val client = OkHttpClient()
                            val request = Request.Builder()
                                .url("https://www.mercadobitcoin.net/api/BTC/ticker/")
                                .build()

                            val response = client.newCall(request).execute()
                            if (response.isSuccessful) {
                                response.body?.string()
                            } else null
                        }

                        if (result != null) {
                            val json = JSONObject(result)
                            val ticker = json.getJSONObject("ticker")
                            val lastValueNum = ticker.getDouble("last")
                            val dateUnix = ticker.getLong("date")


                            val nf = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                            lastValue = nf.format(lastValueNum)


                            val date = Date(dateUnix * 1000)


                            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                            sdf.timeZone = TimeZone.getTimeZone("UTC")


                            val dateInBrazil = sdf.parse(sdf.format(date))

                            val sdfBrazil = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                            sdfBrazil.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")

                            lastDate = sdfBrazil.format(date)

                        } else {
                            Toast.makeText(context, "Erro ao buscar cotação", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Falha: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }
}