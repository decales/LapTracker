package cmpt370.group12.laptracker.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SelectedItemsViewModel : ViewModel() {
    private val selectedItems = mutableStateOf(emptyList<String>())
    //add the item if there's room, dont allow anything to be added
    //if there's already two selected
    fun toggleItem(item: String) {

        val selected = selectedItems.value.toMutableList()

        if ( selected.contains(item)) {

            selected.remove(item)
        } else {

            if ( selected.size < 2) {

                selected.add(item)
            }
        }
        selectedItems.value = selected
    }

}
