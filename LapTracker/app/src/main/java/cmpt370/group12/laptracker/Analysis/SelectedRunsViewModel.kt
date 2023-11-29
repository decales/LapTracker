package cmpt370.group12.laptracker.Analysis

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SelectedRunsViewModel : ViewModel()  {
    val selectedItems = mutableStateOf(emptyList<String>())
    fun toggleItem(item: String){
        val selected = selectedItems.value.toMutableList()
        if (selected.contains(item)){
            selected.remove(item)
        } else {
            selected.add(item)
        }
        selectedItems.value = selected
    }
}