package uz.softex.carsale.pdfservice

import com.itextpdf.io.image.ImageData
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.Color
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.BorderRadius
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import org.springframework.stereotype.Service

@Service
class PdfService {

    fun addImage(path: String, width: Float = 200f, height: Float = 200f): Image {
        val imageData: ImageData = ImageDataFactory.create(path)
        var width1 = imageData.width
        var height1 = imageData.height
        if (width1 < height1) {
            val divide = height * 100 / height1
            height1 = height
            width1 = divide * width1 / 100
        } else {
            val divide = width * 100 / width1
            width1 = width
            height1 = divide * height1 / 100
        }
        return Image(imageData).scaleAbsolute(width1, height1)
    }

    fun addCellAllSettings(
        data: String?,
        table: Table,
        background: Boolean = false,
        fontSize: Float = 10f,
        borderLeft: Boolean = true,
        borderRight: Boolean = true,
        borderTop: Boolean = true,
        borderBottom: Boolean = true,
        textAlignment: TextAlignment = TextAlignment.LEFT,
        color: Color = Color.convertRgbToCmyk(DeviceRgb(245, 245, 245)),
        borderRadius: Boolean = false,
        textColor: Boolean = false
    ) {

        val paragraph = Paragraph(data!!.replace("seats",""))
        if (textColor)
            paragraph.setFontColor(DeviceRgb(140, 140, 140), 1f)
        else
            paragraph.setFontColor(DeviceRgb(70, 66, 85), 1f).setBold()
        paragraph.setFontSize(fontSize)
        paragraph
            .setHorizontalAlignment(HorizontalAlignment.CENTER)
            .setTextAlignment(textAlignment)
        if (data.contains("seats")){
            paragraph.setFontColor(DeviceRgb(255, 255, 255), 1f)}
        val cell = Cell()
        cell.add(paragraph)
        if (borderLeft)
            cell.setBorderLeft(Border.NO_BORDER)
        else cell.setBorderLeft(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))
        if (borderRight)
            cell.setBorderRight(Border.NO_BORDER)
        else cell.setBorderRight(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))

        if (borderTop)
            cell.setBorderTop(Border.NO_BORDER)
        else cell.setBorderTop(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))

        if (borderBottom)
            cell.setBorderBottom(Border.NO_BORDER)
        else cell.setBorderBottom(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))

        if (background)
            cell.setBackgroundColor(color, 100f)
        if (data.contains("seats")) {
            cell.setBackgroundColor(DeviceRgb(0, 163, 137), 1f).setFontColor(DeviceRgb(255, 255, 255), 1f)
        }

        if (borderRadius)
            cell.setBorderRadius(BorderRadius(10f))
        table.addCell(cell).setHorizontalAlignment(HorizontalAlignment.CENTER)
    }

    fun addCellAllSettings(
        data: Table,
        table: Table,
        background: Boolean = false,
        borderLeft: Boolean = true,
        borderRight: Boolean = true,
        borderTop: Boolean = true,
        borderBottom: Boolean = true,
        horizontalAlignment: HorizontalAlignment = HorizontalAlignment.CENTER,
        borderRadius: Boolean = false
    ) {
        val paragraph = Paragraph()
        paragraph.add(data)
        paragraph.setHorizontalAlignment(horizontalAlignment)
        val cell = Cell()
        cell.add(paragraph)


        if (borderLeft)
            cell.setBorderLeft(Border.NO_BORDER)
        else cell.setBorderLeft(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))
        if (borderRight)
            cell.setBorderRight(Border.NO_BORDER)
        else cell.setBorderRight(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))

        if (borderTop)
            cell.setBorderTop(Border.NO_BORDER)
        else cell.setBorderTop(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))

        if (borderBottom)
            cell.setBorderBottom(Border.NO_BORDER)
        else cell.setBorderBottom(SolidBorder(Color.convertRgbToCmyk(DeviceRgb(206, 206, 206)), 0.8f))


        if (background) cell.setBackgroundColor(Color.convertRgbToCmyk(DeviceRgb(245, 245, 245)))
        cell.setHorizontalAlignment(horizontalAlignment)
        if (borderRadius)
            cell.setBorderRadius(BorderRadius(13f))

//    cell.setTextAlignment(TextAlignment.CENTER)
        table.addCell(cell)
    }

    fun addCellAllSettings(
        data: Image,
        table: Table,
        borderLeft: Boolean = true,
        borderRight: Boolean = true,
        borderTop: Boolean = true,
        borderBottom: Boolean = true
    ) {
        val paragraph = Paragraph()
        paragraph.add(data)
        paragraph.setTextAlignment(TextAlignment.CENTER)
        val cell = Cell()
        cell.add(paragraph)
        if (borderLeft)
            cell.setBorderLeft(Border.NO_BORDER)
        if (borderRight)
            cell.setBorderRight(Border.NO_BORDER)
        if (borderTop)
            cell.setBorderTop(Border.NO_BORDER)
        if (borderBottom)
            cell.setBorderBottom(Border.NO_BORDER)
        cell.setVerticalAlignment(VerticalAlignment.TOP)
        table.addCell(cell)
    }

    fun addCellCenterAndBorder(table: Table): Table {
        val big = Table(floatArrayOf(10f, 100f, 10f))
        addCellAllSettings("", big)
        addCellAllSettings(table, big, true, borderRadius = true)
        addCellAllSettings("", big)

        return big
    }
}