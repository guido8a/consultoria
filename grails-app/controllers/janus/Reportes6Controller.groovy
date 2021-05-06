package janus

import com.itextpdf.awt.DefaultFontMapper
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfTemplate
import com.lowagie.text.Document
import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.PageSize
import com.lowagie.text.Paragraph
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import janus.ejecucion.DetallePlanillaCosto
import janus.ejecucion.Planilla

import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.text.DecimalFormat

import org.jfree.chart.ChartFactory
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.CategoryPlot
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.category.BarRenderer
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer
import org.jfree.data.KeyToGroupMap
import org.jfree.data.category.CategoryDataset
import org.jfree.data.category.DefaultCategoryDataset

import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

class Reportes6Controller {

    def dbConnectionService

    def index() { }


    private static int[] arregloEnteros(array) {
        int[] ia = new int[array.size()]
        array.eachWithIndex { it, i ->
            ia[i] = it.toInteger()
        }
        return ia
    }

    private static void addCellTabla(PdfPTable table, paragraph, params) {
        PdfPCell cell = new PdfPCell(paragraph);
        if (params.height) {
            cell.setFixedHeight(params.height.toFloat());
        }
        if (params.border) {
            cell.setBorderColor(params.border);
        }
        if (params.bg) {
            cell.setBackgroundColor(params.bg);
        }
        if (params.colspan) {
            cell.setColspan(params.colspan);
        }
        if (params.align) {
            cell.setHorizontalAlignment(params.align);
        }
        if (params.valign) {
            cell.setVerticalAlignment(params.valign);
        }
        if (params.w) {
            cell.setBorderWidth(params.w);
            cell.setUseBorderPadding(true);
        }
        if (params.bwl) {
            cell.setBorderWidthLeft(params.bwl.toFloat());
            cell.setUseBorderPadding(true);
        }
        if (params.bwb) {
            cell.setBorderWidthBottom(params.bwb.toFloat());
            cell.setUseBorderPadding(true);
        }
        if (params.bwr) {
            cell.setBorderWidthRight(params.bwr.toFloat());
            cell.setUseBorderPadding(true);
        }
        if (params.bwt) {
            cell.setBorderWidthTop(params.bwt.toFloat());
            cell.setUseBorderPadding(true);
        }
        if (params.bcl) {
            cell.setBorderColorLeft(params.bcl);
        }
        if (params.bcb) {
            cell.setBorderColorBottom(params.bcb);
        }
        if (params.bcr) {
            cell.setBorderColorRight(params.bcr);
        }
        if (params.bct) {
            cell.setBorderColorTop(params.bct);
        }
        if (params.padding) {
            cell.setPadding(params.padding.toFloat());
        }
        if (params.pl) {
            cell.setPaddingLeft(params.pl.toFloat());
        }
        if (params.pr) {
            cell.setPaddingRight(params.pr.toFloat());
        }
        if (params.pt) {
            cell.setPaddingTop(params.pt.toFloat());
        }
        if (params.pb) {
            cell.setPaddingBottom(params.pb.toFloat());
        }

        table.addCell(cell);
    }


    def addCellTabla2(table, paragraph, params) {
        PdfPCell cell = new PdfPCell(paragraph);
//        println "params "+params
        cell.setBorderColor(Color.BLACK);

        if (params.border) {
            if (!params.bordeBot)
                if (!params.bordeTop)
                    cell.setBorderColor(params.border);
        }
        if (params.bg) {
            cell.setBackgroundColor(params.bg);
        }
        if (params.colspan) {
            cell.setColspan(params.colspan);
        }
        if (params.align) {
            cell.setHorizontalAlignment(params.align);
        }
        if (params.valign) {
            cell.setVerticalAlignment(params.valign);
        }
        if (params.w) {
            cell.setBorderWidth(params.w);
        }
        if (params.bordeTop) {
            cell.setBorderWidthTop(1)
            cell.setBorderWidthLeft(0)
            cell.setBorderWidthRight(0)
            cell.setBorderWidthBottom(0)
            cell.setPaddingTop(7);

        }
        if (params.bordeBot) {
            cell.setBorderWidthBottom(1)
            cell.setBorderWidthLeft(0)
            cell.setBorderWidthRight(0)
            cell.setPaddingBottom(7)

            if (!params.bordeTop) {
                cell.setBorderWidthTop(0)
            }
        }

        table.addCell(cell);
    }

    private String numero(num, decimales, cero) {
        if (num == 0 && cero.toString().toLowerCase() == "hide") {
            return " ";
        }
        if (decimales == 0) {
            return formatNumber(number: num, minFractionDigits: decimales, maxFractionDigits: decimales, locale: "ec")
        } else {
            def format
            if (decimales == 2) {
                format = "##,##0"
            } else if (decimales == 3) {
                format = "##,###0"
            }
            return formatNumber(number: num, minFractionDigits: decimales, maxFractionDigits: decimales, locale: "ec", format: format)
        }
    }

    private String numero(num, decimales) {
        return numero(num, decimales, "show")
    }

    private String numero(num) {
        return numero(num, 3)
    }

    def reporteOrdenCambio () {

//        println("params " + params)

//        def contrato = Contrato.get(params.id)
//        def planilla = Planilla.get(params.planilla)

        def planilla = Planilla.get(params.id).refresh()
        def contrato = planilla.contrato

        def contratista = contrato.oferta.proveedor
        def strContratista = nombrePersona(contratista, "prov")

        def cn = dbConnectionService.getConnection()
        def cn2 = dbConnectionService.getConnection()
        def sql = "select max(prejfcfn) fecha from prej where prejtipo in ('A', 'P') and cntr__id = ${contrato?.id};"
        def res = cn.rows(sql.toString())
        def fin = res?.first()?.fecha?.format("dd/MM/yyyy")

        def baos = new ByteArrayOutputStream()

        def colorGris = new Color(245, 243, 245);

        Font fontTitle = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
        Font fontTh = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
        Font fontTd = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL);

        Font fontThTiny = new Font(Font.TIMES_ROMAN, 7, Font.BOLD);
        Font fontThTinyN = new Font(Font.TIMES_ROMAN, 7, Font.NORMAL);
        Font fontThTiny2 = new Font(Font.TIMES_ROMAN, 6, Font.BOLD);
        Font fontThTinyN2 = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL);

        def prmsTdNoBorder = [border: Color.WHITE, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE]
        def borderWidth = 0.3

        Locale loc = new Locale("en_US")
        java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance(loc);
        DecimalFormat df = (DecimalFormat)nf;
//        df.applyPattern("\$##,###.##");
        df.applyPattern("##,###.##");

        Document document
        document = new Document(PageSize.A4);
        document.setMargins(50,30,30,28)  // 28 equivale a 1 cm: izq, derecha, arriba y abajo
        def pdfw = PdfWriter.getInstance(document, baos);

        document.open();
        document.addTitle("");
        document.addSubject("Generado por el sistema Janus");
        document.addKeywords("reporte, janus, orden de cambio");
        document.addAuthor("Janus");
        document.addCreator("Tedein SA");


        PdfPTable tabla1 = new PdfPTable(4);
        tabla1.setWidthPercentage(100);
        tabla1.setWidths(arregloEnteros([15,13,47,15]))

        def fondoGris = [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: colorGris, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE]
        def frmtDato = [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE]

        addCellTabla(tabla1, new Paragraph("GOBIERNO AUTÓNOMO DESCENTRALIZADO DE LA PROVINCIA DE PICHINCHA", fontThTiny), fondoGris + [colspan: 4, height: 30])

        addCellTabla(tabla1, new Paragraph("ORDEN DE CAMBIO N° " + (planilla?.numeroOrden ?: ''), fontThTiny), fondoGris + [colspan: 4, height: 30])

        addCellTabla(tabla1, new Paragraph("CONTRATO N°", fontThTiny), fondoGris + [colspan: 2])
        addCellTabla(tabla1, new Paragraph(contrato?.codigo ?: '', fontThTinyN), frmtDato + [colspan: 2])

        addCellTabla(tabla1, new Paragraph("CONTRATISTA:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2])
        addCellTabla(tabla1, new Paragraph(contrato?.contratista?.nombre ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("OBJETO DEL CONTRATO:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2, height: 20])
        addCellTabla(tabla1, new Paragraph(contrato?.objeto ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("MONTO DEL CONTRATO:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2])

        PdfPTable inner1 = new PdfPTable(3);
        addCellTabla(inner1, new Paragraph(df.format(contrato?.monto) + "", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner1, new Paragraph("FECHA DE INICIO CONTRACTUAL:", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner1, new Paragraph(contrato?.obra?.fechaInicio?.format("dd/MM/yyyy"), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(tabla1, inner1, [border: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("PLAZO:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2])

        PdfPTable inner2 = new PdfPTable(3);
        addCellTabla(inner2, new Paragraph((contrato?.plazo?.toInteger() + " días") ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner2, new Paragraph("FECHA DE TÉRMINO CONTRACTUAL:", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner2, new Paragraph( (fin ?: ''), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(tabla1, inner2, [border: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 4, height: 15])

        addCellTabla(tabla1, new Paragraph("De acuerdo al " +
                "informe técnico de Fiscalización en el memorando N°" + (planilla?.memoOrden ?: '') + ", se ha establecido " +
                "la necesidad de ejecutar diferencias de cantidades de obra en el Contrato Original, con la " +
                "finalidad de cumplir efectivamente con el " +
                "objeto del contrato", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 4, height: 30])

        document.add(tabla1)

//        sql = "select * from detalle(${planilla?.contrato?.id}, ${planilla?.contrato?.obra?.id}, ${planilla.id}, '${"T"}')"
        sql = "select rbrocdgo, rbronmbr, unddcdgo, vocrcntd, cntdacml - cntdantr - vocrcntd diff, vocrpcun, vloracml-vlorantr-vocrsbtt vlor from detalle(${planilla?.contrato?.id}, ${planilla?.contrato?.obra?.id}, ${planilla?.id}, 'P') where (cntdacml - cntdantr) > vocrcntd ;"
        def vocr = cn2.rows(sql.toString())


        def existe = 0

        PdfPTable tabla2 = new PdfPTable(7);
        tabla2.setWidthPercentage(100);
        tabla2.setWidths(arregloEnteros([12,35,5,11,12,12,12]))

        addCellTabla(tabla2, new Paragraph("DIFERENCIA DE CANTIDADES DE OBRA", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 20])

        addCellTabla(tabla2, new Paragraph("ITEM", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])
        addCellTabla(tabla2, new Paragraph("DESCRIPCIÓN", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE,  height: 20])
        addCellTabla(tabla2, new Paragraph("U", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])
        addCellTabla(tabla2, new Paragraph("CANTIDAD CONTRATADA", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE,  height: 20])
        addCellTabla(tabla2, new Paragraph("DIFERENCIA DE CANTIDAD", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])
        addCellTabla(tabla2, new Paragraph("PRECIO UNITARIO", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])
        addCellTabla(tabla2, new Paragraph("PRECIO TOTAL", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])

        def total = 0

        vocr.each {mk ->
            addCellTabla(tabla2, new Paragraph(mk?.rbrocdgo ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
            addCellTabla(tabla2, new Paragraph(mk?.rbronmbr ?: '', fontThTinyN2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE,  height: 15])
            addCellTabla(tabla2, new Paragraph(mk?.unddcdgo ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
            addCellTabla(tabla2, new Paragraph((mk?.vocrcntd ?: '')+ '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE,  height: 15])
            addCellTabla(tabla2, new Paragraph((mk?.diff ?: '') + '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
            addCellTabla(tabla2, new Paragraph(mk?.vocrpcun + "", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
            addCellTabla(tabla2, new Paragraph(mk?.vlor + "", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
            total += (mk?.vlor ?: 0)
        }

        def porcentaje = (total / contrato.monto) * 100

        addCellTabla(tabla2, new Paragraph("TOTAL DE EXCEDENTES DE OBRA", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 20])
        addCellTabla(tabla2, new Paragraph(numero(total,2), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE,  height: 20])

        addCellTabla(tabla2, new Paragraph("PORCENTAJE DEL MONTO DEL CONTRATO", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 20])
        addCellTabla(tabla2, new Paragraph(numero(porcentaje, 2), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE,  height: 20])


        addCellTabla(tabla2, new Paragraph("", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 15])

        addCellTabla(tabla2, new Paragraph("Valor que cuenta con la Certificación Presupuestaria N°" + (planilla?.numeroCertificacionOrden ?: '') + ", de fecha " + (planilla?.fechaCertificacionOrden?.format("dd/MM/yyyy") ?: '') + " suscrito por la Coordinación de Administración Presupuestaria", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 15])

        addCellTabla(tabla2, new Paragraph("Adicionalmente el contratista emite la siguiente garantía: " + (planilla?.garantiaOrden ?: ''), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 15])

        addCellTabla(tabla2, new Paragraph("El presente documento se inscribe en la Ley Orgánica para la Eficiencia Sistema Nacional de Contratación Pública, 'Art.9 - Diferencia de Cantidades de Obra, que señala que la " +
                "entidad contratante podrá ordenar y pagar directamente sin necesidad de contrato complementario, hasta el cinco (5%) por ciento del valor reajustado del contrato, " +
                "siempre que no se modifique el objeto contractual'.", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 30])

        addCellTabla(tabla2, new Paragraph("", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 15])

        addCellTabla(tabla2, new Paragraph("Y de acuerdo al memorando N° 2501-DGCP-17, suscrito por el Director de Gestión de Compras Públicas, delegado del señor Prefecto, " +
                "en el que se indica que las 'PARTES' que señala en la Ley Orgánica para la Eficiencia en la Contratación Pública son: la Entidad seccional autónoma como " +
                "contratante y otra persona natural o jurídica como contratista, por lo tanto el GAD de la Provincia de Pichincha, constituye una 'parte' del contrato " +
                "administrativo, representado por el Administrador del contrato, se procesde a suscribit este documento (3 originales) en unidad de acto." , fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 40])

        document.add(tabla2)

        PdfPTable tabla3 = new PdfPTable(7);
        tabla3.setWidthPercentage(100);
        tabla3.setWidths(arregloEnteros([8,23,8,23,8,22,8]))

        addCellTabla(tabla3, new Paragraph("Dado en Quito, " + rep.fechaConFormato(fecha: planilla?.fechaCertificacionOrden, formato: "dd MMMM yyyy").toString(), fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.WHITE, bcl: Color.BLACK, bcr: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 7])

        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.BLACK, bcr: Color.WHITE,  bct: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.BLACK, bct: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])

        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.BLACK, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph((contrato?.administradorContrato?.administrador?.titulo?.toUpperCase() ?: '') + " " + (contrato?.administradorContrato?.administrador?.nombre ?: '') + " " + (contrato?.administradorContrato?.administrador?.apellido ?: ''), fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph(strContratista?.toUpperCase() ?: '' , fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph((contrato?.fiscalizador?.titulo?.toUpperCase() ?: '') + " " + (contrato?.fiscalizador?.nombre ?: '') + " " + (contrato?.fiscalizador?.apellido ?: ''), fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.BLACK,bwb: 0.1, bcb: Color.WHITE, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])

        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.BLACK, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("ADMINISTRADOR DEL CONTRATO", fontThTiny2), [border: Color.BLACK,bct: Color.WHITE, bcl: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("CONTRATISTA", fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE,bct: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("FISCALIZADOR", fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bct: Color.WHITE,bcr: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.BLACK,bwb: 0.1, bcb: Color.BLACK, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])

        document.add(tabla3)

        document.close()
        pdfw.close()
        byte[] b = baos.toByteArray();
        response.setContentType("application/pdf")
        response.setHeader("Content-disposition", "attachment; filename=" + 'ordenDeCambio_' + new Date().format("dd-MM-yyyy"))
        response.setContentLength(b.length)
        response.getOutputStream().write(b)

    }


    def reporteOrdenDeTrabajo () {

        def planilla = Planilla.get(params.id).refresh()
        def contrato = planilla.contrato
        def contratista = contrato.oferta.proveedor
        def strContratista = nombrePersona(contratista, "prov")

        def detalles = DetallePlanillaCosto.findAllByPlanilla(planilla)

        def cn = dbConnectionService.getConnection()
        def sql = "select max(prejfcfn) fecha from prej where prejtipo in ('A', 'P') and cntr__id = ${contrato?.id};"
        def res = cn.rows(sql.toString())
        def fin = res?.first()?.fecha?.format("dd/MM/yyyy")

        def baos = new ByteArrayOutputStream()

        BaseColor colorAzul = new BaseColor(50, 96, 144)

        Font fontTitle = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
        Font fontTh = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
        Font fontTd = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL);

        Font fontThTiny = new Font(Font.TIMES_ROMAN, 7, Font.BOLD);
        Font fontThTinyN = new Font(Font.TIMES_ROMAN, 7, Font.NORMAL);
        Font fontThTiny2 = new Font(Font.TIMES_ROMAN, 6, Font.BOLD);
        Font fontThTinyN2 = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL);

        def prmsTdNoBorder = [border: Color.WHITE, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE]
        def borderWidth = 0.3

        Locale loc = new Locale("en_US")
        java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance(loc);
        DecimalFormat df = (DecimalFormat)nf;
//        df.applyPattern("\$##,###.##");
        df.applyPattern("##,###.##");

        Document document
        document = new Document(PageSize.A4);
        document.setMargins(50,30,30,28)  // 28 equivale a 1 cm: izq, derecha, arriba y abajo
        def pdfw = PdfWriter.getInstance(document, baos);

        document.open();
        document.addTitle("");
        document.addSubject("Generado por el sistema Janus");
        document.addKeywords("reporte, janus, orden de trabajo");
        document.addAuthor("Janus");
        document.addCreator("Tedein SA");


        PdfPTable tabla1 = new PdfPTable(4);
        tabla1.setWidthPercentage(100);
        tabla1.setWidths(arregloEnteros([15,13,47,15]))

        addCellTabla(tabla1, new Paragraph("GOBIERNO AUTÓNOMO DESCENTRALIZADO DE LA PROVINCIA DE PICHINCHA", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 4, height: 30])

        addCellTabla(tabla1, new Paragraph("ORDEN DE TRABAJO N° " + (planilla?.numeroTrabajo ?: '') , fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 4, height: 20])

        addCellTabla(tabla1, new Paragraph("CONTRATO N°", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2])
        addCellTabla(tabla1, new Paragraph(contrato?.codigo ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("CONTRATISTA:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2])
        addCellTabla(tabla1, new Paragraph(contrato?.contratista?.nombre ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("OBJETO DEL CONTRATO:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2, height: 20])
        addCellTabla(tabla1, new Paragraph(contrato?.objeto ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("MONTO DEL CONTRATO:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2])

        PdfPTable inner1 = new PdfPTable(3);
        addCellTabla(inner1, new Paragraph(df.format(contrato?.monto) + "", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner1, new Paragraph("FECHA DE INICIO CONTRACTUAL:", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner1, new Paragraph(contrato?.obra?.fechaInicio?.format("dd/MM/yyyy"), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(tabla1, inner1, [border: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("PLAZO:", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 2])

        PdfPTable inner2 = new PdfPTable(3);
        addCellTabla(inner2, new Paragraph((contrato?.plazo?.toInteger() + " días") ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner2, new Paragraph("FECHA DE TÉRMINO CONTRACTUAL:", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE])
        addCellTabla(inner2, new Paragraph( ( (fin ?: '') + " ") ?: '', fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE])
        addCellTabla(tabla1, inner2, [border: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 2])

        addCellTabla(tabla1, new Paragraph("", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 4, height: 15])

        addCellTabla(tabla1, new Paragraph("De acuerdo al " +
                "informe técnico de Fiscalización en el memorando N° " + (planilla?.memoTrabajo ?: '')+ ", se ha establecido " +
                "la necesidad de ejecutar rubros nuevos  en el Contrato Original, con la " +
                "finalidad de cumplir efectivamente con el " +
                "objeto del contrato", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 4, height: 30])

        document.add(tabla1)

        def total = 0

        PdfPTable tabla2 = new PdfPTable(6);
        tabla2.setWidthPercentage(100);
        tabla2.setWidths(arregloEnteros([10,44,6,10,12,12]))

        addCellTabla(tabla2, new Paragraph("RUBROS NUEVOS", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 20])

        addCellTabla(tabla2, new Paragraph("ITEM", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])
        addCellTabla(tabla2, new Paragraph("DESCRIPCIÓN", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE,  height: 20])
        addCellTabla(tabla2, new Paragraph("U", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])
        addCellTabla(tabla2, new Paragraph("CANTIDAD", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE,  height: 20])
        addCellTabla(tabla2, new Paragraph("PRECIO REFERENCIAL", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])
        addCellTabla(tabla2, new Paragraph("PRECIO TOTAL", fontThTiny2), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 20])

        detalles.each{ dt->
            def tot = dt.monto + dt.montoIndirectos
            addCellTabla(tabla2, new Paragraph(dt?.factura, fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
            addCellTabla(tabla2, new Paragraph(dt?.rubro, fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE,  height: 15])
            addCellTabla(tabla2, new Paragraph(dt?.unidad?.codigo, fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
            addCellTabla(tabla2, new Paragraph(numero(dt?.cantidad, 2), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE,  height: 15])
            addCellTabla(tabla2, new Paragraph(numero((tot / dt?.cantidad),2) + "", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
            addCellTabla(tabla2, new Paragraph(numero(tot,2) + "", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
            total += (tot ?: 0)
        }

        def porcentaje = (total / contrato.monto) *100

        addCellTabla(tabla2, new Paragraph("TOTAL DE RUBROS NUEVOS", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 5, height: 20])
        addCellTabla(tabla2, new Paragraph(numero(total,2), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE,  height: 20])

        addCellTabla(tabla2, new Paragraph("PORCENTAJE RESPECTO AL MONTO DEL CONTRATO", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.LIGHT_GRAY, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 5, height: 20])
        addCellTabla(tabla2, new Paragraph(numero(porcentaje, 2), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE,  height: 20])

        addCellTabla(tabla2, new Paragraph("", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 7, height: 15])

        addCellTabla(tabla2, new Paragraph("Valor que cuenta con la Certificación Presupuestaria N° " + (planilla?.numeroCertificacionTrabajo ?: '')+ ", de fecha " + (planilla?.fechaCertificacionTrabajo?.format("dd/MM/yyyy") ?: '') + " suscrito por la Coordinación de Administración Presupuestaria", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 15])

        addCellTabla(tabla2, new Paragraph("Adicionalmente el contratista emite la siguiente garantía de FIEL CUMPLIMIENTO  N° " + (planilla?.garantiaTrabajo ?: ''), fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 15])

        addCellTabla(tabla2, new Paragraph("El presente documento se inscribe en la Ley Orgánica para la eficiencia del Sistema Nacional de Contratación Pública, 'Art.10.- ORDENES DE TRABAJO:- La Entidad " +
                "Contratante podrá disponer, durante la ejecución de la obra, hasta del dos (2%) por ciento del valor actualizado o reajustado del contrato principal, para la realización " +
                "de rubros nuevos, mediante órdenes de trabajo y empleando la modalidad de costo más porcentaje. En todo caso, los rescursos deberán estar presupuestados de conformidad con la " +
                "presente Ley. Las órdenes de trabajo contendrán las firmas de las partes dy de la fiscalización'.", fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 40])

        addCellTabla(tabla2, new Paragraph("", fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 15])

        addCellTabla(tabla2, new Paragraph("Y de acuerdo al memorando N° 2501-DGCP-17, suscrito por el Director de Gestión de Compras Públicas, delegado del señor Prefecto, " +
                "en el que se indica que las 'PARTES' que señala en la Ley Orgánica para la Eficiencia en la Contratación Pública son: la Entidad seccional autónoma como " +
                "contratante y otra persona natural o jurídica como contratista, por lo tanto el GAD de la Provincia de Pichincha, constituye una 'parte' del contrato " +
                "administrativo, representado por el Administrador del contrato, se procesde a suscribit este documento (3 originales) en unidad de acto." , fontThTinyN), [border: Color.BLACK, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, colspan: 6, height: 40])

        document.add(tabla2)

        PdfPTable tabla3 = new PdfPTable(7);
        tabla3.setWidthPercentage(100);
        tabla3.setWidths(arregloEnteros([8,23,8,23,8,22,8]))

        addCellTabla(tabla3, new Paragraph("Dado en Quito, " + rep.fechaConFormato(fecha: planilla?.fechaCertificacionTrabajo, formato: "dd MMMM yyyy").toString(), fontThTiny), [border: Color.BLACK, bwb: 0.1, bcb: Color.WHITE, bcl: Color.BLACK, bcr: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_LEFT, valign: Element.ALIGN_MIDDLE, colspan: 7])

        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.BLACK, bcr: Color.WHITE,  bct: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.BLACK, bct: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 50])

        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.BLACK, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph((contrato?.administradorContrato?.administrador?.titulo?.toUpperCase() ?: '') + " " + (contrato?.administradorContrato?.administrador?.nombre ?: '') + " " + (contrato?.administradorContrato?.administrador?.apellido ?: ''), fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph(strContratista?.toUpperCase() ?: '' , fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph((contrato?.fiscalizador?.titulo?.toUpperCase() ?: '') + " " + (contrato?.fiscalizador?.nombre ?: '') + " " + (contrato?.fiscalizador?.apellido ?: ''), fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE,bwb: 0.1, bcb: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 30])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.BLACK,bwb: 0.1, bcb: Color.WHITE, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 30])

        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.BLACK, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("ADMINISTRADOR DEL CONTRATO", fontThTiny2), [border: Color.BLACK,bct: Color.WHITE, bcl: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE, bct: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("CONTRATISTA", fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE,bct: Color.WHITE, bcr: Color.WHITE, bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("FISCALIZADOR", fontThTiny2), [border: Color.BLACK, bcl: Color.WHITE, bct: Color.WHITE,bcr: Color.WHITE,bwb: 0.1, bcb: Color.BLACK, bg: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, height: 15])
        addCellTabla(tabla3, new Paragraph("", fontThTiny), [border: Color.BLACK, bcl: Color.WHITE, bcr: Color.BLACK,bwb: 0.1, bcb: Color.BLACK, bct: Color.WHITE, bg: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE, height: 15])

        document.add(tabla3)

        document.close()
        pdfw.close()
        byte[] b = baos.toByteArray();
        response.setContentType("application/pdf")
        response.setHeader("Content-disposition", "attachment; filename=" + 'ordenDeTrabajo_' + new Date().format("dd-MM-yyyy"))
        response.setContentLength(b.length)
        response.getOutputStream().write(b)

    }


    private String cap(str) {
        return str.replaceAll(/[a-zA-Z_0-9áéíóúÁÉÍÓÚñÑüÜ]+/, {
            it[0].toUpperCase() + ((it.size() > 1) ? it[1..-1].toLowerCase() : '')
        })
    }

    private String nombrePersona(persona, tipo) {
        def str = ""
        if (persona) {
            switch (tipo) {
                case "pers":
                    str = cap((persona.titulo ? persona.titulo + " " : "") + persona.nombre + " " + persona.apellido)
                    break;
                case "prov":
                    if(persona.tipo == 'N'){
                        str = cap((persona.titulo ? persona.titulo + " " : "") + persona.nombreContacto + " " + persona.apellidoContacto)
                    } else {
                        str = cap(persona.nombreContacto)
                    }
                    break;
            }
        }
        return str
    }


    def graficoAvance () {
        def cn = dbConnectionService.getConnection()
        def data = []
        def cont = 0

        def sql = "select cntnnmbr, sum(cntrmnto) contratado, avg(avncecon)::numeric(6,2)*100 economico, " +
                "avg(avncfsco)::numeric(6,2) fisico from rp_contrato() group by cntnnmbr order by 2 desc;"
        def datos = cn.rows(sql.toString())

        com.itextpdf.text.Font fontTitulo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 14, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font fontTtlo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18, com.itextpdf.text.Font.BOLD);

        def tipo = params.tipo
        def subtitulo = 'AVANCE DE OBRAS'
        def tituloArchivo = 'Por Cantón'

        data = []

        cn.eachRow(sql.toString()) { d ->
            data.add([nmro: cont, nmbr: d.cntnnmbr, vlor: d.contratado, econ: d.economico, fsco: d.fisico])
            cont++
        }
//        println "data: $data"

        def baos = new ByteArrayOutputStream()

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4);
        def pdfw = com.itextpdf.text.pdf.PdfWriter.getInstance(document, baos);

        document.open();

        com.itextpdf.text.Paragraph parrafoUniversidad = new com.itextpdf.text.Paragraph("GOBIERNO DE PICHINCHA", fontTitulo)
        parrafoUniversidad.setAlignment(com.lowagie.text.Element.ALIGN_CENTER)
        com.itextpdf.text.Paragraph parrafoFacultad = new com.itextpdf.text.Paragraph("", fontTitulo)
        parrafoFacultad.setAlignment(com.lowagie.text.Element.ALIGN_CENTER)
        com.itextpdf.text.Paragraph parrafoEscuela = new com.itextpdf.text.Paragraph("", fontTitulo)
        parrafoEscuela.setAlignment(com.lowagie.text.Element.ALIGN_CENTER)
        com.itextpdf.text.Paragraph linea = new com.itextpdf.text.Paragraph(" ", fontTitulo)
        parrafoFacultad.setAlignment(com.lowagie.text.Element.ALIGN_CENTER)

        com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph(subtitulo, fontTtlo)
        titulo.setAlignment(com.lowagie.text.Element.ALIGN_CENTER)

        document.add(parrafoUniversidad)
        document.add(parrafoFacultad)
        document.add(parrafoEscuela)
        document.add(linea)
//        document.add(titulo)

        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        def ancho = 500
        def alto = 300

        try {

            PdfContentByte contentByte = pdfw.getDirectContent();

            com.itextpdf.text.Paragraph parrafo1 = new com.itextpdf.text.Paragraph();
            com.itextpdf.text.Paragraph parrafo2 = new com.itextpdf.text.Paragraph();

            PdfTemplate template = contentByte.createTemplate(ancho, alto);
            PdfTemplate template2 = contentByte.createTemplate(ancho, alto/10);
            Graphics2D graphics2d = template.createGraphics(ancho, alto, new DefaultFontMapper());
            Graphics2D graphics2d2 = template2.createGraphics(ancho, alto/10, new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, ancho, alto);

            //color
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();

            Color color = new Color(79, 129, 189);
            renderer.setSeriesPaint(0, color);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            Image chartImage = Image.getInstance(template);
            parrafo1.add(chartImage);

            graphics2d2.dispose();
            Image chartImage3 = Image.getInstance(template2);
            parrafo2.add(chartImage3);

            document.add(parrafo1)
            document.add(parrafo2)


        } catch (Exception e) {
            e.printStackTrace();
        }

        float[] columnas = [18,75,30,20,20]

        com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(columnas); // 3 columns.
        table.setWidthPercentage(100);
        com.itextpdf.text.pdf.PdfPTable table2 = new com.itextpdf.text.pdf.PdfPTable(columnas); // 3 columns.
        table2.setWidthPercentage(100);

        com.itextpdf.text.pdf.PdfPCell cell1 = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph("Leyenda"))
        com.itextpdf.text.pdf.PdfPCell cell2 = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph("Cantón"));
        com.itextpdf.text.pdf.PdfPCell cell3 = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph("Valor Total Contratado"));
        com.itextpdf.text.pdf.PdfPCell cell4 = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph("Avance Económico"));
        com.itextpdf.text.pdf.PdfPCell cell5 = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph("Avance Físico"));

        cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell2.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell3.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell4.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell5.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell4.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        cell5.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)

        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY)
        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY)
        cell3.setBackgroundColor(BaseColor.LIGHT_GRAY)
        cell4.setBackgroundColor(BaseColor.LIGHT_GRAY)
        cell5.setBackgroundColor(BaseColor.LIGHT_GRAY)

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);

        data.each { d ->
            table2.addCell(crearCelda(tipo, 'G' + (d.nmro), 'C' + (d.nmro + 1)))
//            println "------> ${d.fsco}, ${d.econ}"
            table2.addCell(crearCeldaTexto(d.nmbr))
            table2.addCell(crearCeldaNumero(numero(d.vlor, 2)))
            table2.addCell(crearCeldaNumero(numero(d.econ, 2) + '%'))
            table2.addCell(crearCeldaNumero(numero(d.fsco, 2) + '%'))
        }

        document.add(table);
        document.add(table2);

        document.close();
        pdfw.close()
        byte[] b = baos.toByteArray();
        response.setContentType("application/pdf")
        response.setHeader("Content-disposition", "attachment; filename=" + "reporte_de_avance_" + new Date().format("dd-MM-yyyy") + ".pdf")
        response.setContentLength(b.length)
        response.getOutputStream().write(b)

    }

    def crearCelda (tipo,g,e) {
        com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph(tipo == '1' ? g : e));
        cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER)
        return cell
    }

    def crearCeldaTexto (txt) {
        com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph(txt));
        return cell
    }

    def crearCeldaNumero (txt) {
        com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph(txt));
        cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT)
        return cell
    }


    private JFreeChart createChart(final CategoryDataset dataset) {

        final JFreeChart chart = ChartFactory.createBarChart("Avance de Obras Contratadas", // chart
                // title
                "Cantones", // domain axis label
                "Contratado", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = chart.getCategoryPlot();
//        plot.setBackgroundPaint(Color.lightGray);
        plot.setBackgroundPaint(Color.white);
//        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, Color.lightGray);
        final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, Color.lightGray);
        final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, Color.lightGray);
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

        return chart;
    }


    private CategoryDataset createDataset() {

        def cn = dbConnectionService.getConnection()
        def data = [:]
        def parts1 = []
        def parts2 = []

        def sql = "select cntnnmbr, sum(cntrmnto) contratado, avg(avncecon)::numeric(6,2)*100 economico, " +
                "avg(avncfsco)::numeric(6,2) fisico from rp_contrato() group by cntnnmbr order by 2 desc"
        def datos = cn.rows(sql.toString())
        data = [:]
        cn.eachRow(sql.toString()) { d ->
            data.put((d.cntnnmbr), d.contratado + "_" + d.economico + "_" + d.fisico )
        }

        def tam = data.size()
        def ges = []
        def ees = []

        tam.times{
            ees.add('C' + (it + 1))
        }

        final String series1 = "Contratado";
        final String series2 = "Avance Económico";
        final String series3 = "Avance Físico";

        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        data.eachWithIndex { q, k ->

//            println("q " + q + " k " + k)

            parts1[k] = q.value.split("_")
            parts2[k] = q.key

            //1
            dataset.addValue( parts1[k][0].toDouble() , series1 ,  ees[k]);
            //2
            dataset.addValue( (parts1[k][1].toDouble() * parts1[k][0].toDouble() / 100)  , series2 ,  ees[k]);
            //3
            dataset.addValue( (parts1[k][2].toDouble() * parts1[k][0].toDouble() / 100) , series3 ,  ees[k]);
        }

        return dataset;
    }

    def meses = ['', "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]

    private String printFecha(Date fecha) {
        if (fecha) {
            return (fecha.format("dd") + ' de ' + meses[fecha.format("MM").toInteger()] + ' de ' + fecha.format("yyyy"))
        } else {
            return "Error: no hay fecha que mostrar"
        }
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }



    def reporteComposicion() {

        def obra = Obra.get(params.id)
        def totales
        def valorTotal
        def total1 = 0
        def totalesMano
        def valorTotalMano
        def total2 = 0
        def totalesEquipos
        def valorTotalEquipos
        def total3 = 0
        def total4 = 0
        def total5 = 0
        def total6 = 0
        def total7 = 0
        def total8 = 0
        def total9 = 0
        def total10 = 0

        if (!params.tipo) {
            params.tipo = "-1"
        }
        if (!params.rend) {
            params.rend = "screen"
        }
        if (!params.sp) {
            params.sp = '-1'
        }
        if (params.tipo == "-1") {
            params.tipo = "1,2,3"
        }
        def wsp = ""
        if (params.sp.toString() != "-1") {
            wsp = "      AND v.sbpr__id = ${params.sp} \n"
        }



        def sql = "SELECT i.itemcdgo codigo, i.itemnmbr item, u.unddcdgo unidad, sum(v.voitcntd) cantidad, \n" +
                "v.voitpcun punitario, v.voittrnp transporte, v.voitpcun + v.voittrnp  costo, \n" +
                "sum((v.voitpcun + v.voittrnp) * v.voitcntd)  total, g.grpodscr grupo, i.grcs__id grid \n" +
                "FROM vlobitem v INNER JOIN item i ON v.item__id = i.item__id\n" +
                "INNER JOIN undd u ON i.undd__id = u.undd__id\n" +
                "INNER JOIN dprt d ON i.dprt__id = d.dprt__id\n" +
                "INNER JOIN sbgr s ON d.sbgr__id = s.sbgr__id\n" +
                "INNER JOIN grpo g ON s.grpo__id = g.grpo__id AND g.grpo__id IN (${params.tipo}) \n" +
                "WHERE v.obra__id = ${params.id} and v.voitcntd >0 \n" + wsp +
                "group by i.itemcdgo, i.itemnmbr, u.unddcdgo, v.voitpcun, v.voittrnp, v.voitpcun, \n" +
                "i.grcs__id, g.grpodscr " +
                "ORDER BY i.grcs__id ASC, i.itemcdgo"


        def cn = dbConnectionService.getConnection()
        def res = cn.rows(sql.toString())

//        println("sql " + sql)

        def baos = new ByteArrayOutputStream()
        def name = "composicion_" + new Date().format("ddMMyyyy_hhmm") + ".pdf";
        Font times12bold = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
        Font times10bold = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
        Font times18bold = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
        Font times14bold = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
        Font times16bold = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
        Font times8bold = new Font(Font.TIMES_ROMAN, 8, Font.BOLD)
        Font times8normal = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)
        Font times10boldWhite = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
        Font times8boldWhite = new Font(Font.TIMES_ROMAN, 8, Font.BOLD)
        times8boldWhite.setColor(Color.BLACK)
        times10boldWhite.setColor(Color.BLACK)
        def fonts = [times12bold: times12bold, times10bold: times10bold, times8bold: times8bold,
                     times10boldWhite: times10boldWhite, times8boldWhite: times8boldWhite, times8normal: times8normal]

        Document document
        document = new Document(PageSize.A4);
        def pdfw = PdfWriter.getInstance(document, baos);
        document.open();
        document.addTitle("Composicion " + new Date().format("dd_MM_yyyy"));
        document.addSubject("Generado por el sistema Janus");
        document.addKeywords("reporte, janus, composicion");
        document.addAuthor("Janus");
        document.addCreator("Tedein SA");

        def prmsHeaderHoja = [border: Color.WHITE]
        def prmsHeader = [border: Color.WHITE, colspan: 7,
                          align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE]
        def prmsRight = [border: Color.WHITE, colspan: 7,
                         align: Element.ALIGN_RIGHT, valign: Element.ALIGN_RIGHT]
        def prmsHeader2 = [border: Color.WHITE, colspan: 3,
                           align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE]
        def prmsCellHead = [border: Color.WHITE,
                            align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE]
        def prmsCellHead3 = [border: Color.WHITE,
                             align: Element.ALIGN_LEFT, valign: Element.ALIGN_LEFT]
        def prmsCellHead2 = [border: Color.WHITE,
                             align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE, bordeTop: "1", bordeBot: "1"]
        def prmsCellIzquierda = [border: Color.WHITE,
                                 align: Element.ALIGN_LEFT, valign: Element.ALIGN_LEFT]
        def prmsCellDerecha = [border: Color.WHITE,
                               align: Element.ALIGN_RIGHT, valign: Element.ALIGN_RIGHT]
        def prmsCellDerecha2 = [border: Color.WHITE,
                                align: Element.ALIGN_RIGHT, valign: Element.ALIGN_RIGHT, bordeTop: "1", bordeBot: "1"]
        def prmsCellCenter = [border: Color.WHITE, align: Element.ALIGN_CENTER, valign: Element.ALIGN_MIDDLE]
        def prmsCellLeft = [border: Color.WHITE, valign: Element.ALIGN_MIDDLE]
        def prmsSubtotal = [border: Color.WHITE, colspan: 6,
                            align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE]
        def prmsNum = [border: Color.WHITE, align: Element.ALIGN_RIGHT, valign: Element.ALIGN_MIDDLE]

        def prms = [prmsHeaderHoja: prmsHeaderHoja, prmsHeader: prmsHeader, prmsHeader2: prmsHeader2,
                    prmsCellHead: prmsCellHead, prmsCell: prmsCellCenter, prmsCellLeft: prmsCellLeft, prmsSubtotal: prmsSubtotal, prmsNum: prmsNum, prmsRight: prmsRight,
                    prmsCellDerecha: prmsCellDerecha, prmsCellIzquierda: prmsCellIzquierda]

        Paragraph headersTitulo = new Paragraph();
        addEmptyLine(headersTitulo, 1);
        headersTitulo.setAlignment(Element.ALIGN_CENTER);
        headersTitulo.add(new Paragraph("SEP - G.A.D. PROVINCIA DE PICHINCHA", times18bold));
        headersTitulo.add(new Paragraph("COMPOSICIÓN", times14bold));
        headersTitulo.add(new Paragraph(obra?.departamento?.direccion?.nombre, times12bold));
        headersTitulo.add(new Paragraph("", times12bold));
        document.add(headersTitulo)

        PdfPTable header = new PdfPTable(3)
        header.setWidthPercentage(100)
        header.setWidths(arregloEnteros([25, 8, 65]))

        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("OBRA", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(" : ", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(obra?.nombre, times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("CÓDIGO", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(" : ", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(obra?.codigo, times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("DOCUMENTO DE REFERENCIA", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(" : ", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(obra?.oficioIngreso, times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("FECHA", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(" : ", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(printFecha(obra?.fechaCreacionObra), times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("FECHA ACT. PRECIOS", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(" : ", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph(printFecha(obra?.fechaPreciosRubros), times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)
        addCellTabla(header, new Paragraph("", times8bold), prmsCellHead3)

        document.add(header);


        PdfPTable tablaHeader = new PdfPTable(8)
        tablaHeader.setWidthPercentage(100)
        tablaHeader.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        PdfPTable tablaTitulo = new PdfPTable(2)
        tablaTitulo.setWidthPercentage(100)
        tablaTitulo.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion = new PdfPTable(8)
        tablaComposicion.setWidthPercentage(100)
        tablaComposicion.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        PdfPTable tablaTotales = new PdfPTable(2)
        tablaTotales.setWidthPercentage(100)
        tablaTotales.setWidths(arregloEnteros([70, 30]))

        addCellTabla2(tablaHeader, new Paragraph("Código", times8bold), prmsCellHead2)
        addCellTabla2(tablaHeader, new Paragraph("Item", times8bold), prmsCellHead2)
        addCellTabla2(tablaHeader, new Paragraph("U", times8bold), prmsCellHead2)
        addCellTabla2(tablaHeader, new Paragraph("Cantidad", times8bold), prmsCellHead2)
        addCellTabla2(tablaHeader, new Paragraph("Precio Unitario", times8bold), prmsCellDerecha2)
        addCellTabla2(tablaHeader, new Paragraph("Transporte", times8bold), prmsCellDerecha2)
        addCellTabla2(tablaHeader, new Paragraph("Costo", times8bold), prmsCellDerecha2)
        addCellTabla2(tablaHeader, new Paragraph("Total", times8bold), prmsCellDerecha2)

        PdfPTable tablaDirectos = new PdfPTable(2)
        tablaDirectos.setWidthPercentage(100)
        tablaDirectos.setWidths(arregloEnteros([90, 10]))

        addCellTabla(tablaDirectos, new Paragraph("COSTOS DIRECTOS ", times14bold), prmsCellIzquierda)
        addCellTabla(tablaDirectos, new Paragraph(" ", times10bold), prmsCellIzquierda)

        addCellTabla(tablaTitulo, new Paragraph("Remuneraciones ", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { r ->

            if (r?.grid == 1) {

                addCellTabla(tablaComposicion, new Paragraph(r?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion, new Paragraph(r?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion, new Paragraph(r?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion, new Paragraph(g.formatNumber(number: r?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion, new Paragraph(g.formatNumber(number: r?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion, new Paragraph(g.formatNumber(number: r?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion, new Paragraph(g.formatNumber(number: r?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion, new Paragraph(g.formatNumber(number: r?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                totales = r?.total
                valorTotal = (total1 += totales)
            }

        }

//        addCellTabla(tablaTotales, new Paragraph("Total Materiales", times10bold), prmsCellDerecha)
//        addCellTabla(tablaTotales, new Paragraph(g.formatNumber(number: valorTotal, minFractionDigits:
//                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

//        addCellTabla(tablaTotales, new Paragraph(" ", times10bold), prmsNum)
//        addCellTabla(tablaTotales, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTitulo2 = new PdfPTable(2)
        tablaTitulo2.setWidthPercentage(100)
        tablaTitulo2.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion2 = new PdfPTable(8)
        tablaComposicion2.setWidthPercentage(100)
        tablaComposicion2.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        PdfPTable tablaTotalesMano = new PdfPTable(2)
        tablaTotalesMano.setWidthPercentage(100)
        tablaTotalesMano.setWidths(arregloEnteros([70, 30]))

        addCellTabla(tablaTitulo2, new Paragraph("Viajes y Viáticos", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo2, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { j ->
            if (j?.grid == 2) {
                addCellTabla(tablaComposicion2, new Paragraph(j?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion2, new Paragraph(j?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion2, new Paragraph(j?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion2, new Paragraph(g.formatNumber(number: j?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion2, new Paragraph(g.formatNumber(number: j?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion2, new Paragraph(g.formatNumber(number: j?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion2, new Paragraph(g.formatNumber(number: j?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion2, new Paragraph(g.formatNumber(number: j?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                totalesMano = j?.total
                valorTotalMano = (total2 += totalesMano)
            }

        }

//        addCellTabla(tablaTotalesMano, new Paragraph("Total Mano de Obra:", times10bold), prmsCellDerecha)
//        addCellTabla(tablaTotalesMano, new Paragraph(g.formatNumber(number: valorTotalMano, minFractionDigits:
//                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

//        addCellTabla(tablaTotalesMano, new Paragraph(" ", times10bold), prmsNum)
//        addCellTabla(tablaTotalesMano, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTitulo3 = new PdfPTable(2)
        tablaTitulo3.setWidthPercentage(100)
        tablaTitulo3.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion3 = new PdfPTable(8)
        tablaComposicion3.setWidthPercentage(100)
        tablaComposicion3.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        PdfPTable tablaTotalesEquipos = new PdfPTable(2)
        tablaTotalesEquipos.setWidthPercentage(100)
        tablaTotalesEquipos.setWidths(arregloEnteros([70, 30]))

        addCellTabla(tablaTitulo3, new Paragraph("Materiales ", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo3, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 3) {
                addCellTabla(tablaComposicion3, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion3, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion3, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion3, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion3, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion3, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion3, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion3, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                totalesEquipos = k?.total
                valorTotalEquipos = (total3 += totalesEquipos)
            }

        }

//        addCellTabla(tablaTotalesEquipos, new Paragraph("Total Equipos:", times10bold), prmsCellDerecha)
//        addCellTabla(tablaTotalesEquipos, new Paragraph(g.formatNumber(number: valorTotalEquipos, minFractionDigits:
//                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)


//        addCellTabla(tablaComposicion3, new Paragraph(" ", times10bold), prmsNum)
//        addCellTabla(tablaComposicion3, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTitulo4 = new PdfPTable(2)
        tablaTitulo4.setWidthPercentage(100)
        tablaTitulo4.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion4 = new PdfPTable(8)
        tablaComposicion4.setWidthPercentage(100)
        tablaComposicion4.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        addCellTabla(tablaTitulo4, new Paragraph("Mano de Obra ", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo4, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 4) {
                addCellTabla(tablaComposicion4, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion4, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion4, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion4, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion4, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion4, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion4, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion4, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                total4 += k?.total
            }
        }

        addCellTabla(tablaComposicion4, new Paragraph(" ", times10bold), prmsNum)
        addCellTabla(tablaComposicion4, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTitulo5 = new PdfPTable(2)
        tablaTitulo5.setWidthPercentage(100)
        tablaTitulo5.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion5 = new PdfPTable(8)
        tablaComposicion5.setWidthPercentage(100)
        tablaComposicion5.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        addCellTabla(tablaTitulo5, new Paragraph("Equipo", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo5, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 5) {
                addCellTabla(tablaComposicion5, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion5, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion5, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion5, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion5, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion5, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion5, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion5, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                total5 += k?.total
            }
        }

        addCellTabla(tablaComposicion5, new Paragraph(" ", times10bold), prmsNum)
        addCellTabla(tablaComposicion5, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTitulo6 = new PdfPTable(2)
        tablaTitulo6.setWidthPercentage(100)
        tablaTitulo6.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion6 = new PdfPTable(8)
        tablaComposicion6.setWidthPercentage(100)
        tablaComposicion6.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        addCellTabla(tablaTitulo6, new Paragraph("Reproducciones", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo6, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 6) {
                addCellTabla(tablaComposicion6, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion6, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion6, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion6, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion6, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion6, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion6, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion6, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                total6 += k?.total
            }
        }

        addCellTabla(tablaComposicion6, new Paragraph(" ", times10bold), prmsNum)
        addCellTabla(tablaComposicion6, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTitulo7 = new PdfPTable(2)
        tablaTitulo7.setWidthPercentage(100)
        tablaTitulo7.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion7 = new PdfPTable(8)
        tablaComposicion7.setWidthPercentage(100)
        tablaComposicion7.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        addCellTabla(tablaTitulo7, new Paragraph("Equipos e Instalaciones", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo7, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 7) {
                addCellTabla(tablaComposicion7, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion7, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion7, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion7, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion7, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion7, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion7, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion7, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                total7 += k?.total
            }
        }

        PdfPTable tablaTotales1 = new PdfPTable(2)
        tablaTotales1.setWidthPercentage(100)
        tablaTotales1.setWidths(arregloEnteros([90, 10]))

        addCellTabla(tablaTotales1, new Paragraph("TOTAL COSTO DIRECTO:", times10bold), prmsCellDerecha)
        addCellTabla(tablaTotales1, new Paragraph(g.formatNumber(number: (valorTotal + valorTotalMano + valorTotalEquipos + total4 + total5 + total6 + total7), minFractionDigits:
                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

        addCellTabla(tablaComposicion7, new Paragraph(" ", times10bold), prmsNum)
        addCellTabla(tablaComposicion7, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaIndirectos = new PdfPTable(2)
        tablaIndirectos.setWidthPercentage(100)
        tablaIndirectos.setWidths(arregloEnteros([90, 10]))

        addCellTabla(tablaIndirectos, new Paragraph("COSTOS INDIRECTOS ", times14bold), prmsCellIzquierda)
        addCellTabla(tablaIndirectos, new Paragraph(" ", times10bold), prmsCellIzquierda)

        PdfPTable tablaTitulo8 = new PdfPTable(2)
        tablaTitulo8.setWidthPercentage(100)
        tablaTitulo8.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion8 = new PdfPTable(8)
        tablaComposicion8.setWidthPercentage(100)
        tablaComposicion8.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        addCellTabla(tablaTitulo8, new Paragraph("Personal de Dirección", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo8, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 8) {
                addCellTabla(tablaComposicion8, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion8, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion8, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion8, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion8, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion8, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion8, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion8, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                total8 += k?.total
            }
        }

        addCellTabla(tablaComposicion8, new Paragraph(" ", times10bold), prmsNum)
        addCellTabla(tablaComposicion8, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTitulo9 = new PdfPTable(2)
        tablaTitulo9.setWidthPercentage(100)
        tablaTitulo9.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion9 = new PdfPTable(8)
        tablaComposicion9.setWidthPercentage(100)
        tablaComposicion9.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        addCellTabla(tablaTitulo9, new Paragraph("Servicios Varios", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo9, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 9) {
                addCellTabla(tablaComposicion9, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion9, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion9, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion9, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion9, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion9, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion9, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion9, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                total9 += k?.total
            }
        }

        PdfPTable tablaTotales2 = new PdfPTable(2)
        tablaTotales2.setWidthPercentage(100)
        tablaTotales2.setWidths(arregloEnteros([90, 10]))

        addCellTabla(tablaTotales2, new Paragraph("TOTAL COSTO INDIRECTO:", times10bold), prmsCellDerecha)
        addCellTabla(tablaTotales2, new Paragraph(g.formatNumber(number: (total8 + total9), minFractionDigits:
                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

        addCellTabla(tablaComposicion9, new Paragraph(" ", times10bold), prmsNum)
        addCellTabla(tablaComposicion9, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaGenerales = new PdfPTable(2)
        tablaGenerales.setWidthPercentage(100)
        tablaGenerales.setWidths(arregloEnteros([90, 10]))

        addCellTabla(tablaGenerales, new Paragraph("GASTOS GENERALES ", times14bold), prmsCellIzquierda)
        addCellTabla(tablaGenerales, new Paragraph(" ", times10bold), prmsCellIzquierda)

        PdfPTable tablaTitulo10 = new PdfPTable(2)
        tablaTitulo10.setWidthPercentage(100)
        tablaTitulo10.setWidths(arregloEnteros([90, 10]))

        PdfPTable tablaComposicion10 = new PdfPTable(8)
        tablaComposicion10.setWidthPercentage(100)
        tablaComposicion10.setWidths(arregloEnteros([12, 36, 5, 9, 9, 9, 10, 10]))

        addCellTabla(tablaTitulo10, new Paragraph("Gastos Generales", times12bold), prmsCellIzquierda)
        addCellTabla(tablaTitulo10, new Paragraph(" ", times10bold), prmsCellIzquierda)

        res.each { k ->
            if (k?.grid == 10) {
                addCellTabla(tablaComposicion10, new Paragraph(k?.codigo, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion10, new Paragraph(k?.item, times8normal), prmsCellIzquierda)
                addCellTabla(tablaComposicion10, new Paragraph(k?.unidad, times8normal), prmsCellHead)
                addCellTabla(tablaComposicion10, new Paragraph(g.formatNumber(number: k?.cantidad, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion10, new Paragraph(g.formatNumber(number: k?.punitario, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion10, new Paragraph(g.formatNumber(number: k?.transporte, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion10, new Paragraph(g.formatNumber(number: k?.costo, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)
                addCellTabla(tablaComposicion10, new Paragraph(g.formatNumber(number: k?.total, minFractionDigits:
                        3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times8normal), prmsNum)

                total10 += k?.total
            }
        }

        PdfPTable tablaTotales3 = new PdfPTable(2)
        tablaTotales3.setWidthPercentage(100)
        tablaTotales3.setWidths(arregloEnteros([90, 10]))

        addCellTabla(tablaTotales3, new Paragraph("TOTAL GASTOS GENERALES:", times10bold), prmsCellDerecha)
        addCellTabla(tablaTotales3, new Paragraph(g.formatNumber(number: (total10), minFractionDigits:
                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

        addCellTabla(tablaTotales3, new Paragraph(" ", times10bold), prmsNum)
        addCellTabla(tablaTotales3, new Paragraph(" ", times10bold), prmsNum)

        PdfPTable tablaTotalGeneral = new PdfPTable(2)
        tablaTotalGeneral.setWidthPercentage(100)
        tablaTotalGeneral.setWidths(arregloEnteros([90, 10]))

        addCellTabla(tablaTotalGeneral, new Paragraph("Subtotal Costo:", times10bold), prmsCellDerecha)
        addCellTabla(tablaTotalGeneral, new Paragraph(g.formatNumber(number: (valorTotal + valorTotalMano + valorTotalEquipos + total4 + total5 + total6 + total7 + total8 + total9 + total10), minFractionDigits:
                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

        addCellTabla(tablaTotalGeneral, new Paragraph("Utilidad empresarial:", times10bold), prmsCellDerecha)
        addCellTabla(tablaTotalGeneral, new Paragraph(g.formatNumber(number: (obra?.valor ? obra?.valor - (valorTotal + valorTotalMano + valorTotalEquipos + total4 + total5 + total6 + total7 + total8 + total9 + total10) : 0), minFractionDigits:
                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

        addCellTabla(tablaTotalGeneral, new Paragraph("Total:", times10bold), prmsCellDerecha)
        addCellTabla(tablaTotalGeneral, new Paragraph(g.formatNumber(number: (obra?.valor ?: 0), minFractionDigits:
                3, maxFractionDigits: 3, format: "##,##0", locale: "ec"), times10bold), prmsNum)

        document.add(tablaHeader);
        document.add(tablaDirectos);
        document.add(tablaTitulo);
        document.add(tablaComposicion);
        document.add(tablaTotales)
        document.add(tablaTitulo2)
        document.add(tablaComposicion2);
        document.add(tablaTotalesMano)
        document.add(tablaTitulo3)
        document.add(tablaComposicion3);
        document.add(tablaTitulo4)
        document.add(tablaComposicion4);
        document.add(tablaTitulo5)
        document.add(tablaComposicion5);
        document.add(tablaTitulo6)
        document.add(tablaComposicion6);
        document.add(tablaTitulo7)
        document.add(tablaComposicion7);
        document.add(tablaTotales1);
        document.add(tablaIndirectos);
        document.add(tablaTitulo8)
        document.add(tablaComposicion8);
        document.add(tablaTitulo9)
        document.add(tablaComposicion9);
        document.add(tablaTotales2);
        document.add(tablaGenerales);
        document.add(tablaTitulo10)
        document.add(tablaComposicion10);
        document.add(tablaTotales3);
        document.add(tablaTotalesEquipos)
        document.add(tablaTotalGeneral)
        document.close();
        pdfw.close()
        byte[] b = baos.toByteArray();
        response.setContentType("application/pdf")
        response.setHeader("Content-disposition", "attachment; filename=" + name)
        response.setContentLength(b.length)
        response.getOutputStream().write(b)
    }

}
