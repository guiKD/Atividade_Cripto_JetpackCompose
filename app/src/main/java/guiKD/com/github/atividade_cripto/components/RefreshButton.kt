package guiKD.com.github.atividade_cripto.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RefreshButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(top = 16.dp)
            .then(Modifier),
    ) {
        Text(
            text = "ATUALIZAR",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}