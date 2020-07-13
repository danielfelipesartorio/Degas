package com.sartorio.degas.ui.clients.importclient

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.*
import com.sartorio.degas.repository.ClientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.InputStream

class ImportClientViewModel constructor(
    private val clientRepository: ClientRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    val clientUpdatedCount = ObservableField<Int>()
    val newClientCount = ObservableField<Int>()
    val error = ObservableField<String>()

    private var headerMap = mutableMapOf<Int, String>()

    private val tempClientList = mutableListOf<Client>()

    fun processFile(inputStream: InputStream) {
        error.set(null)
        try {
            val myFileSystem = POIFSFileSystem(inputStream)
            val myWorkBook = HSSFWorkbook(myFileSystem)
            val mySheet = myWorkBook.getSheetAt(0)

            val firstRow = mySheet.getRow(0)
            val firstRowIterator = firstRow.cellIterator()
            while (firstRowIterator.hasNext()) {
                val myCell = firstRowIterator.next() as HSSFCell
                headerMap[myCell.columnIndex] = myCell.toString()
            }

            val rowIter = mySheet.rowIterator()
            while (rowIter.hasNext()) {
                val myRow = rowIter.next() as HSSFRow
                if (myRow.rowNum != 0) {
                    val cellIter = myRow.cellIterator()
                    var razaoSocial = ""
                    var fantasia = ""
                    var cpfcnpj = ""
                    var inscricao = ""
                    var cep = ""
                    var nomedarua = ""
                    var bairro = ""
                    var cidade = ""
                    var estado = ""
                    var nomecontato = ""
                    var email = ""
                    var telefone = ""
                    var celular = ""
                    while (cellIter.hasNext()) {
                        val myCell = cellIter.next() as HSSFCell
                        when (headerMap[myCell.columnIndex]) {
                            RAZAO_SOCIAL -> {
                                razaoSocial = myCell.toString()
                            }
                            NOME_FANTASIA -> {
                                fantasia = myCell.toString()
                            }
                            CPF_CNPJ -> {
                                cpfcnpj = myCell.toString()
                            }
                            INSCRICAO_ESTADUAL -> {
                                inscricao = myCell.toString()
                            }
                            CEP -> {
                                cep = myCell.toString()
                            }
                            NOME_DA_RUA -> {
                                nomedarua = myCell.toString()
                            }
                            BAIRRO -> {
                                bairro = myCell.toString()
                            }
                            CIDADE -> {
                                cidade = myCell.toString()
                            }
                            ESTADO -> {
                                estado = myCell.toString()
                            }
                            NOME_CONTATO -> {
                                nomecontato = myCell.toString()
                            }
                            EMAIL -> {
                                email = myCell.toString()
                            }
                            TELEFONE -> {
                                telefone = myCell.toString()
                            }
                            CELULAR -> {
                                celular = myCell.toString()
                            }
                        }
                    }
                    if (razaoSocial.isNotEmpty()) {
                        tempClientList.add(
                            Client(
                                razaoSocial,
                                ClientName(razaoSocial, fantasia),
                                ClientDocuments(cpfcnpj, inscricao),
                                ClientAddress(nomedarua, cep, bairro, cidade, estado),
                                ClientContact(nomecontato, email, telefone, celular)
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TESTE", "" + e + e.message)
            error.set(e.message)
        }

        coroutineScope.launch {
            val currentProductsList = clientRepository.getClientsList()

            val codesInTheFile = tempClientList.map { it.uName }
            val unchangedCodes: MutableList<Client> =
                currentProductsList.filter { it.uName !in codesInTheFile }.toMutableList()

            clientUpdatedCount.set(currentProductsList.size - unchangedCodes.size)

            val newProductsList = mutableListOf<Client>()
            newProductsList.addAll(tempClientList)
            newProductsList.addAll(unchangedCodes)

            newClientCount.set(newProductsList.size - currentProductsList.size)

            clientRepository.updateClientList(newProductsList)
        }
    }

    private fun mapColors(myCell: HSSFCell): List<Int> {
        return myCell.toString().splitToSequence(",").toList().map { it.toInt() }
    }

    companion object {
        const val RAZAO_SOCIAL = "RAZAO SOCIAL"
        const val NOME_FANTASIA = "NOME FANTASIA"
        const val CPF_CNPJ = "CPF/CNPJ"
        const val INSCRICAO_ESTADUAL = "INSCRICAO ESTADUAL"
        const val CEP = "CEP"
        const val NOME_DA_RUA = "NOME DA RUA"
        const val BAIRRO = "BAIRRO"
        const val CIDADE = "CIDADE"
        const val ESTADO = "ESTADO"
        const val NOME_CONTATO = "NOME CONTATO"
        const val EMAIL = "EMAIL"
        const val TELEFONE = "TELEFONE"
        const val CELULAR = "CELULAR"
    }
}