package cmpt370.group12.laptracker.Testing

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.domain.model.MapPoint
import kotlin.random.Random

@Composable
fun Start_Test(test: TestDatabaseViewModel = viewModel()){

    val expected = MapPoint(
        null,
        (0..100).random(),
        Random.nextDouble(),
        Random.nextDouble(),
        (0..50).random().toString(),
        (0..20).random()
    )
    test.Test_InsertMapPoint(expected)

}