package arsensaliev.io.kotlinapp.ui.main.interfaces

import arsensaliev.io.kotlinapp.data.model.Note

// В методичке этот интерфейс был внутри файла адаптера.
// Думал это не правильно, так как этот интерфейс может
// использоваться не только в адаптере но в другом месте
// возомжно может быть переиспользована
interface OnItemClickListener {
    fun onItemClick(note: Note)
}