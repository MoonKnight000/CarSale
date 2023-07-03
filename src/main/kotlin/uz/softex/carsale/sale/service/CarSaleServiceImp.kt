package uz.softex.carsale.sale.service

import com.itextpdf.kernel.colors.Color
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.BorderRadius
import com.itextpdf.layout.properties.TextAlignment
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.FileCopyUtils
import uz.softex.carsale.car.exception.CarCountIsZero
import uz.softex.carsale.car.exception.CarNotFound
import uz.softex.carsale.car.repository.CarRepository
import uz.softex.carsale.company.exception.CompanyNotFound
import uz.softex.carsale.company.repository.CompanyRepository
import uz.softex.carsale.payload.ApiResponse
import uz.softex.carsale.payload.ApiResponseGeneric
import uz.softex.carsale.pdfservice.PdfService
import uz.softex.carsale.sale.dto.SaleCarDto
import uz.softex.carsale.sale.entity.SaleCar
import uz.softex.carsale.sale.exception.ContractNotFound
import uz.softex.carsale.sale.exception.PhotoNotFound
import uz.softex.carsale.sale.repository.SaleCarRepository
import uz.softex.carsale.user.service.AuthService
import java.io.FileInputStream

@Service
class CarSaleServiceImp(
    private val repository: SaleCarRepository,
    private val carRepository: CarRepository,
    private val authService: AuthService,
    private val pdfService: PdfService,
    private val companyRepository: CompanyRepository
) : CarSaleService {
    override fun getAllSales(): ApiResponseGeneric<*> {
        val findAll = repository.findAll()
        val dtoList = mutableListOf<SaleCarDto>()
        findAll.forEach { dtoList.add(SaleCarDto(it)) }
        return ApiResponseGeneric(dtoList)
    }

    override fun getSalesById(id: Int): ApiResponseGeneric<*> {
        return ApiResponseGeneric(SaleCarDto(repository.findById(id).orElseThrow { throw ContractNotFound() }))
    }

    @Transactional
    override fun saleCar(dto: SaleCarDto): ApiResponse {
        val car = carRepository.findById(dto.car!!).orElseThrow { throw CarNotFound() }
        if (car.count!! <= 0) throw CarCountIsZero()
        val saleCar = SaleCar()
        if (dto.discount!! < 0 || dto.discount!! > 100) throw ContractNotFound()
        saleCar.car = car
        println(car.price!! * (100 - dto.discount!!) / 100)
        println("---------------------------------------")
        saleCar.allPrice = car.price!! * (100 - dto.discount!!) / 100
        saleCar.discount = dto.discount
        saleCar.month = dto.month
        if (dto.month!! < 0)
            saleCar.month = 0
        saleCar.firstPayment = dto.firstPayment
        if (saleCar.firstPayment!! > saleCar.allPrice!!) saleCar.monthlyPayment = 0
        else
            saleCar.monthlyPayment = (saleCar.allPrice!! - dto.firstPayment!!) / dto.month!!
        saleCar.buyPerson = authService.getCurrentUser()
        saleCar.saleCompany = car.model!!.company
        repository.save(saleCar)
        car.count = car.count!! - 1
        carRepository.save(car)
        return ApiResponse()
    }

    override fun update(dto: SaleCarDto): ApiResponse {
        val car = carRepository.findById(dto.car!!).orElseThrow { throw CarNotFound() }
        if (car.count!! <= 0) throw CarCountIsZero()
        val saleCar = repository.findById(dto.id!!).orElseThrow { throw ContractNotFound() }
        saleCar.car!!.count = saleCar.car!!.count!! + 1
        carRepository.save(saleCar.car!!)
        saleCar.car = car
        saleCar.allPrice = car.price!! * (0.100 - dto.discount!! / 100).toInt()
        saleCar.discount = dto.discount
        saleCar.month = dto.month
        saleCar.firstPayment = dto.firstPayment
        saleCar.monthlyPayment = (saleCar.allPrice!! - dto.firstPayment!!) / dto.month!!
        saleCar.buyPerson = authService.getCurrentUser()
        saleCar.saleCompany = companyRepository.findById(dto.saleCompany!!).orElseThrow { throw CompanyNotFound() }
        if (saleCar.saleCompany != car.model!!.company) throw CarNotFound()
        repository.save(saleCar)
        car.count = car.count!! - 1
        carRepository.save(car)
        return ApiResponse()
    }

    override fun delete(id: Int): ApiResponse {
        if (!repository.existsById(id)) throw ContractNotFound()
        repository.deleteById(id)
        return ApiResponse()
    }

    override fun contractToPdf(id: Int, response: HttpServletResponse): ApiResponse {
        val contract = repository.findById(id).orElseThrow { throw ContractNotFound() }
        val company = contract.car!!.model!!.company!!
        if (company.image == null) throw PhotoNotFound()
        val car = contract.car!!
        if (car.images.size <= 1) throw PhotoNotFound()
        val pdfWriter = PdfWriter("F:\\CarSaleImages/copy.pdf")
        val pdfDocument = PdfDocument(pdfWriter)
        pdfDocument.addNewPage(PageSize.A4.rotate())
        val document = Document(pdfDocument)
        document.setMargins(0f, 0f, 0f, 0f)
        val mainTable = Table(floatArrayOf(100f, 130f, 80f))


        val firstMainCellTable = Table(floatArrayOf(100f))

        pdfService.addCellAllSettings(pdfService.addImage("${company.image!!.path}", 103f, 130f), firstMainCellTable)
        pdfService.addCellAllSettings(
            "   Mashina:",
            firstMainCellTable,
            false,
            12f,
            true,
            true,
            true,
            true,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            "${car.model!!.name}",
            firstMainCellTable,
            false,
            15f,
            true,
            true,
            true,
            true,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            pdfService.addImage("${car.images[0].path}", 150f, 150f),
            firstMainCellTable
        )
        pdfService.addCellAllSettings("\n\n\n\n\n\n\n\n\n\n\n\n", firstMainCellTable)
        pdfService.addCellAllSettings(
            pdfService.addImage("D:\\fayllar/927963d5-d40b-4834-9390-806647469426.jpg", 130f, 130f), firstMainCellTable
        )

        //second
        pdfService.addCellAllSettings(firstMainCellTable, mainTable, false, true, false)
        val secondMainCellTable = Table(floatArrayOf(100f))
        val secondMainFirstCellTable = Table(floatArrayOf(80f, 120f, 80f, 100f, 100f, 100f, 50f))

        pdfService.addCellAllSettings("\n", secondMainCellTable, false)
        pdfService.addCellAllSettings("", secondMainFirstCellTable, false)
        pdfService.addCellAllSettings("Yili", secondMainFirstCellTable, false, 8f, true, false, textColor = true)
        pdfService.addCellAllSettings(
            "Rangi",
            secondMainFirstCellTable,
            false,
            8f,
            false,
            false,
            true,
            true,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings(
            "Motori",
            secondMainFirstCellTable,
            false,
            10f,
            false,
            false,
            true,
            true,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings(
            "Tezligi",
            secondMainFirstCellTable,
            false,
            10f,
            false,
            false,
            true,
            true,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings(
            "Holati",
            secondMainFirstCellTable,
            false,
            10f,
            false,
            true,
            true,
            true,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings("", secondMainFirstCellTable, true)
        pdfService.addCellAllSettings("", secondMainFirstCellTable, true)

        pdfService.addCellAllSettings(
            "${car.dateOfProduced.year}",
            secondMainFirstCellTable,
            true,
            10f,
            true,
            false,
            true,
            false
        )
        pdfService.addCellAllSettings(
            "${car.color}",
            secondMainFirstCellTable,
            false,
            10f,
            false,
            false,
            true,
            false,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            "${car.model!!.engine}",
            secondMainFirstCellTable,
            true,
            10f,
            false,
            false,
            true,
            false,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            "${car.model!!.speed}km/h",
            secondMainFirstCellTable,
            false,
            10f,
            false,
            false,
            true,
            false,
            TextAlignment.CENTER
        )
        var holati = "yangi"
        if (car.used)
            holati = "minilgan"
        pdfService.addCellAllSettings(
            holati,
            secondMainFirstCellTable,
            false,
            10f,
            false,
            true,
            true,
            false,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings("", secondMainFirstCellTable, true)


        val reasonToSell = Table(floatArrayOf(50f, 200f))
        pdfService.addCellAllSettings("", reasonToSell, true)
        pdfService.addCellAllSettings(
            "Sotish sababi:                                                                ",
            reasonToSell,
            true,
            textColor = true
        )
        pdfService.addCellAllSettings("", reasonToSell, true)
        pdfService.addCellAllSettings("Minish yoqmay qoldi", reasonToSell, true, 11f)
        val all = Table(floatArrayOf(100f))
        pdfService.addCellAllSettings(secondMainFirstCellTable, all)
        pdfService.addCellAllSettings(reasonToSell, all)

        pdfService.addCellAllSettings(pdfService.addCellCenterAndBorder(all), secondMainCellTable, borderRadius = true)

        pdfService.addCellAllSettings(
            "Mashinani ko'rinishi",
            secondMainCellTable,
            false,
            15f,
            textAlignment = TextAlignment.CENTER,
            textColor = true
        )

        val secondMainCellImagesTable = Table(floatArrayOf(270f, 250f))
        pdfService.addCellAllSettings(
            pdfService.addImage("${car.images[1].path}", 200f, 200f),
            secondMainCellImagesTable
        )
        pdfService.addCellAllSettings(
            pdfService.addImage("${car.images[2].path}", 200f, 200f),
            secondMainCellImagesTable
        )

        pdfService.addCellAllSettings(secondMainCellImagesTable, secondMainCellTable, false)

        val secondMainThirdCellTable = Table(floatArrayOf(60f, 100f, 50f, 100f, 50f, 80f, 60f))
        pdfService.addCellAllSettings("", secondMainThirdCellTable)
        pdfService.addCellAllSettings(
            "Boshlang`ich to`lov",
            secondMainThirdCellTable,
            false,
            8f,
            true,
            true,
            true,
            false,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings(
            "chegirma(%)",
            secondMainThirdCellTable,
            false,
            8f,
            true,
            true,
            true,
            false,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings(
            "sotuv summasi",
            secondMainThirdCellTable,
            false,
            8f,
            true,
            true,
            true,
            false,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings(
            "muddat(oy)",
            secondMainThirdCellTable,
            false,
            8f,
            true,
            true,
            true,
            false,
            TextAlignment.CENTER, textColor = true
        )
        pdfService.addCellAllSettings(
            "oylik to`lov",
            secondMainThirdCellTable,
            false,
            8f,
            true,
            true,
            true,
            false,
            TextAlignment.CENTER,
            textColor = true
        )
        pdfService.addCellAllSettings("", secondMainThirdCellTable, false)
        pdfService.addCellAllSettings("", secondMainThirdCellTable, false)


        pdfService.addCellAllSettings(
            "${contract.firstPayment}",
            secondMainThirdCellTable,
            true,
            12f,
            true,
            true,
            false,
            true,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            "${contract.discount}%",
            secondMainThirdCellTable,
            true,
            12f,
            true,
            true,
            false,
            true,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            "${contract.allPrice}",
            secondMainThirdCellTable,
            true,
            12f,
            true,
            true,
            false,
            true,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            "${contract.month}",
            secondMainThirdCellTable,
            true,
            12f,
            true,
            true,
            false,
            true,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings(
            "${contract.monthlyPayment}",
            secondMainThirdCellTable,
            true,
            12f,
            true,
            true,
            false,
            true,
            TextAlignment.CENTER
        )
        pdfService.addCellAllSettings("", secondMainThirdCellTable)

        pdfService.addCellAllSettings(
            pdfService.addCellCenterAndBorder(secondMainThirdCellTable),
            secondMainCellTable,
            borderRadius = true
        )

        pdfService.addCellAllSettings(secondMainCellTable, mainTable, false, false)
        val thirdMainCellTable = Table(floatArrayOf(250f))

        val thirdMainCellFirstTable = Table(floatArrayOf(200f, 200f))
        pdfService.addCellAllSettings("O`rindiqlar soni ", thirdMainCellFirstTable, true, textColor = true)

        val numberTable = Table(floatArrayOf(120f, 60f)).setBorderRadius(BorderRadius(5.73f))
            .setPadding(5.73f)

        pdfService.addCellAllSettings("", numberTable)
        pdfService.addCellAllSettings(
            "${car.model!!.seats}seats",
            numberTable,
            true,
            15f,
            textAlignment = TextAlignment.RIGHT,
            color = Color.convertRgbToCmyk(DeviceRgb(0, 163, 137)), borderRadius = true
        )
        pdfService.addCellAllSettings(numberTable, thirdMainCellFirstTable, true)

        pdfService.addCellAllSettings("Benzin bakining hajmi:", thirdMainCellFirstTable, true, textColor = true)
        pdfService.addCellAllSettings("10L  ", thirdMainCellFirstTable, true, 12f, textAlignment = TextAlignment.RIGHT)
        pdfService.addCellAllSettings("Narxi:", thirdMainCellFirstTable, true, textColor = true)
        pdfService.addCellAllSettings(
            "${car.price}   UZS  ",
            thirdMainCellFirstTable,
            true,
            15f,
            true,
            true,
            true,
            true,
            TextAlignment.RIGHT
        )

        pdfService.addCellAllSettings(thirdMainCellFirstTable, thirdMainCellTable, true, borderBottom = false)


        pdfService.addCellAllSettings("Boshlang`ich tolov", thirdMainCellTable, true, 10f, textColor = true)
        pdfService.addCellAllSettings("${contract.firstPayment}", thirdMainCellTable, true, 13f)
        pdfService.addCellAllSettings("Oylik tolov", thirdMainCellTable, true, textColor = true)
        pdfService.addCellAllSettings("${contract.monthlyPayment}", thirdMainCellTable, true, 13f)
        pdfService.addCellAllSettings("Sotuv summasi", thirdMainCellTable, true, textColor = true)


        pdfService.addCellAllSettings("${contract.allPrice} UZS", thirdMainCellTable, true, 15f)

        pdfService.addCellAllSettings("\n\n\n\n\n\n\n\n\n", thirdMainCellTable, true)
        pdfService.addCellAllSettings("Sotuv ofis manzili", thirdMainCellTable, true, 8f, textColor = true)
        pdfService.addCellAllSettings("Navoiy , O`zbekiston 12", thirdMainCellTable, true, 13f)
        pdfService.addCellAllSettings("Call Markazi", thirdMainCellTable, true, 9f, textColor = true)
        pdfService.addCellAllSettings(company.phoneNumber, thirdMainCellTable, true, 13f)
        pdfService.addCellAllSettings(
            "Taklif 19.06.2023, 14:53 da yaratilgan. Amal qilish muddati 3 kun. Bu ommaviy taklif emas. Eng so'nggi ma'lumotlar uchun tanlangan mashina uchun savdo ofisiga murojaat qiling.\n\n\n",
            thirdMainCellTable,
            true,
            textColor = true
        )


        pdfService.addCellAllSettings(thirdMainCellTable, mainTable)

        mainTable.children.forEachIndexed { index, iElement ->
            val cell = iElement as Cell
            cell.setBorder(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0f))
        }

        document.add(mainTable)
        document.close()
        //application/pdf
        response.setHeader("Content-Disposition", "attachment; filename=\"copy.pdf\"");
        response.contentType = "application/pdf"
        FileCopyUtils.copy(FileInputStream("F:\\CarSaleImages/copy.pdf"), response.outputStream)
        return ApiResponse()
    }

    override fun getMyContracts(): ApiResponseGeneric<*> {
        val findByBuyPersonId = repository.findByBuyPersonId(authService.getCurrentUser().id!!)
        val dtoList = mutableListOf<SaleCarDto>()
        findByBuyPersonId.forEach { dtoList.add(SaleCarDto(it)) }
        return ApiResponseGeneric(dtoList)
    }
}