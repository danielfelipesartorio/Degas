import ir.mirrajabi.searchdialog.core.Searchable


class SampleSearchModel(private var mTitle: String) : Searchable {

    override fun getTitle(): String {
        return mTitle
    }

    fun setTitle(title: String): SampleSearchModel {
        mTitle = title
        return this
    }
}