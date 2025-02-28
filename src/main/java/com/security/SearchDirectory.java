package com.security;


import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Stream;

public class SearchDirectory {


    private static final String DOC = "doc";
    private static final String DOCX = "docx";
    private static final String LOG = "log";
    private static final String MSG = "msg";
    private static final String ODT = "odt";
    private static final String PAGES = "pages";
    private static final String RTF = "rtf";
    private static final String TEX = "tex";
    private static final String TXT = "txt";
    private static final String WPD = "wpd";
    private static final String WPS = "wps";
    private static final String HWP = "hwp";


    private static final String CSV = "csv";
    private static final String DAT = "dat";
    private static final String GBR = "gbr";
    private static final String GED = "ged";
    private static final String KEY = "key";
    private static final String KEYCHAIN = "keychain";
    private static final String PPS = "pps";
    private static final String PPT = "ppt";
    private static final String PPTX = "pptx";
    private static final String SDF = "sdf";
    private static final String TAR = "tar";
    private static final String TAX2012 = "tax2012";
    private static final String TAX2014 = "tax2014";
    private static final String VCF = "vcf";
    private static final String XML = "xml";


    private static final String ALF = "alf";
    private static final String IFF = "iff";
    private static final String M3U = "m3u";
    private static final String M4A = "m4a";
    private static final String MID = "mid";
    private static final String MP3 = "mp3";
    private static final String MPA = "mpa";
    private static final String RA = "ra";
    private static final String WAV = "wav";
    private static final String WMA = "wma";

    // Video files
    private static final String G32 = "3g2";
    private static final String G3P = "3gp";
    private static final String ASF = "asf";
    private static final String ASX = "asx";
    private static final String AVI = "avi";
    private static final String FLV = "flv";
    private static final String M4V = "m4v";
    private static final String MOV = "mov";
    private static final String MP4 = "mp4";
    private static final String MPG = "mpg";
    private static final String RM = "rm";
    private static final String SRT = "srt";
    private static final String SWF = "swf";
    private static final String VOB = "vob";
    private static final String WMV = "wmv";


    // 3D image files
    private static final String D3M = "3dm";
    private static final String D3S = "3ds";
    private static final String MAX = "max";
    private static final String OBJ = "obj";


    // Raster image files
    private static final String BMP = "bmp";
    private static final String DDA = "dda";
    private static final String GIF = "gif";
    private static final String JPG = "jpg";
    private static final String PNG = "png";
    private static final String PSD = "psd";
    private static final String PSIMAGE = "pspimage";
    private static final String TGA = "tga";
    private static final String THM = "thm";
    private static final String TIF = "tif";
    private static final String TIFF = "tiff";
    private static final String YUV = "yuv";


    // Vector image files
    private static final String AI = "ai";
    private static final String EPS = "eps";
    private static final String PS = "ps";
    private static final String SVG = "svg";

    // Page layout files
    private static final String INDD = "indd";
    private static final String PCT = "pct";
    private static final String PDF = "pdf";


    // Spreadsheet files
    private static final String XLR = "xlr";
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";


    // Compressed files
    private static final String Z7 = "7z";
    private static final String RAR = "rar";
    private static final String ZIP = "zip";
    private static final String TARGZ = "tar.gz";


    // Executable files
    private static final String APK = "apk";
    private static final String APP = "app";
    private static final String COM = "com";
    private static final String EXE = "exe";


    // Web files
    private static final String ASP = "asp";
    private static final String ASPX = "apsx";
    private static final String CSS = "css";
    private static final String HTM = "htm";
    private static final String HTML = "html";
    private static final String JS = "js";
    private static final String JSP = "jsp";
    private static final String PHP = "php";
    private static final String XHTML = "xhtml";

    // Font files
    private static final String FNT = "fnt";
    private static final String FONT = "font";
    private static final String OFT = "oft";
    private static final String TTF = "ttf";

    // Mick files
    private static final String CRDOWNLAOD = "crdownload";
    private static final String ICS = "ics";
    private static final String MSI = "msi";
    private static final String PART = "part";
    private static final String TORRENT = "torrent";


    private final HashMap<String, String> docMap;
    private final HashMap<String, String> docxMap;
    private final HashMap<String, String> logMap;
    private final HashMap<String, String> msgMap;
    private final HashMap<String, String> odtMap;
    private final HashMap<String, String> pagesMap;
    private final HashMap<String, String> rtfMap;
    private final HashMap<String, String> texMap;
    private final HashMap<String, String> txtMap;
    private final HashMap<String, String> wpdMap;
    private final HashMap<String, String> wpsMap;
    private final HashMap<String, String> hwpMap;


    private final HashMap<String, String> csvMap;
    private final HashMap<String, String> datMap;
    private final HashMap<String, String> gbrMap;
    private final HashMap<String, String> gedMap;
    private final HashMap<String, String> keyMap;
    private final HashMap<String, String> keychainMap;
    private final HashMap<String, String> ppsMap;
    private final HashMap<String, String> pptMap;
    private final HashMap<String, String> pptxMap;
    private final HashMap<String, String> sdfMap;
    private final HashMap<String, String> tarMap;
    private final HashMap<String, String> tax2012Map;
    private final HashMap<String, String> tax2014Map;
    private final HashMap<String, String> vcfMap;
    private final HashMap<String, String> xmlMap;


    private final HashMap<String, String> alfMap;
    private final HashMap<String, String> iffMap;
    private final HashMap<String, String> m3uMap;
    private final HashMap<String, String> m4aMap;
    private final HashMap<String, String> midMap;
    private final HashMap<String, String> mp3Map;
    private final HashMap<String, String> mpaMap;
    private final HashMap<String, String> raMap;
    private final HashMap<String, String> wavMap;
    private final HashMap<String, String> wmaMap;


    private final HashMap<String, String> g2Map;
    private final HashMap<String, String> gpMap;
    private final HashMap<String, String> asfMap;
    private final HashMap<String, String> asxMap;
    private final HashMap<String, String> aviMap;
    private final HashMap<String, String> flvlvMap;
    private final HashMap<String, String> m4vMap;
    private final HashMap<String, String> movMap;
    private final HashMap<String, String> mp4Map;
    private final HashMap<String, String> mpgMap;
    private final HashMap<String, String> rmMap;
    private final HashMap<String, String> srtMap;
    private final HashMap<String, String> swfMap;
    private final HashMap<String, String> vobMap;
    private final HashMap<String, String> wmvMap;


    private final HashMap<String, String> d3mMap;
    private final HashMap<String, String> d3sMap;
    private final HashMap<String, String> maxMap;
    private final HashMap<String, String> objMap;

    private final HashMap<String, String> bmpMap;
    private final HashMap<String, String> ddaMap;
    private final HashMap<String, String> gifMap;
    private final HashMap<String, String> jpgMap;
    private final HashMap<String, String> pngMap;
    private final HashMap<String, String> psdMap;
    private final HashMap<String, String> pspimageMap;
    private final HashMap<String, String> tgaMap;
    private final HashMap<String, String> thmMap;
    private final HashMap<String, String> tifMap;
    private final HashMap<String, String> tiffMap;
    private final HashMap<String, String> yuvMap;


    private final HashMap<String, String> aiMap;
    private final HashMap<String, String> epsMap;
    private final HashMap<String, String> psMap;
    private final HashMap<String, String> svgMap;


    private final HashMap<String, String> inddMap;
    private final HashMap<String, String> pctMap;
    private final HashMap<String, String> pdfMap;

    private final HashMap<String, String> xlrMap;
    private final HashMap<String, String> xlsMap;
    private final HashMap<String, String> xlsxMap;


    private final HashMap<String, String> z7Map;
    private final HashMap<String, String> rarMap;
    private final HashMap<String, String> zipMap;
    private final HashMap<String, String> targzMap;


    private final HashMap<String, String> apkMap;
    private final HashMap<String, String> appMap;
    private final HashMap<String, String> comMap;
    private final HashMap<String, String> exeMap;


    private final HashMap<String, String> aspMap;
    private final HashMap<String, String> aspxMap;
    private final HashMap<String, String> cssMap;
    private final HashMap<String, String> htmMap;
    private final HashMap<String, String> htmlMap;
    private final HashMap<String, String> jsMap;
    private final HashMap<String, String> jspMap;
    private final HashMap<String, String> phpMap;
    private final HashMap<String, String> xhtmlMap;

    private final HashMap<String, String> fntMap;
    private final HashMap<String, String> fontMap;
    private final HashMap<String, String> oftMap;
    private final HashMap<String, String> ttfMap;


    private final HashMap<String, String> crdownloadMap;
    private final HashMap<String, String> icsMap;
    private final HashMap<String, String> msiMap;
    private final HashMap<String, String> partMap;
    private final HashMap<String, String> torrentMap;


    private final TreeMap<String, HashMap<String, String>> containsFilters;
    private String PathtoFind;


    public SearchDirectory(String PathtoFind) {
        this.docMap = new HashMap<String, String>();
        this.docxMap = new HashMap<String, String>();
        this.logMap = new HashMap<String, String>();
        this.msgMap = new HashMap<String, String>();
        this.odtMap = new HashMap<String, String>();
        this.pagesMap = new HashMap<String, String>();
        this.rtfMap = new HashMap<String, String>();
        this.texMap = new HashMap<String, String>();
        this.txtMap = new HashMap<String, String>();
        this.wpdMap = new HashMap<String, String>();
        this.wpsMap = new HashMap<String, String>();
        this.hwpMap = new HashMap<String, String>();


        this.csvMap = new HashMap<String, String>();
        this.datMap = new HashMap<String, String>();
        this.gbrMap = new HashMap<String, String>();
        this.gedMap = new HashMap<String, String>();
        this.keyMap = new HashMap<String, String>();
        this.keychainMap = new HashMap<String, String>();
        this.ppsMap = new HashMap<String, String>();
        this.pptMap = new HashMap<String, String>();
        this.pptxMap = new HashMap<String, String>();
        this.sdfMap = new HashMap<String, String>();
        this.tarMap = new HashMap<String, String>();
        this.tax2012Map = new HashMap<String, String>();
        this.tax2014Map = new HashMap<String, String>();
        this.vcfMap = new HashMap<String, String>();
        this.xmlMap = new HashMap<String, String>();


        this.alfMap = new HashMap<String, String>();
        this.iffMap = new HashMap<String, String>();
        this.m3uMap = new HashMap<String, String>();
        this.m4aMap = new HashMap<String, String>();
        this.midMap = new HashMap<String, String>();
        this.mp3Map = new HashMap<String, String>();
        this.mpaMap = new HashMap<String, String>();
        this.raMap = new HashMap<String, String>();
        this.wavMap = new HashMap<String, String>();
        this.wmaMap = new HashMap<String, String>();


        this.g2Map = new HashMap<String, String>();
        this.gpMap = new HashMap<String, String>();
        this.asfMap = new HashMap<String, String>();
        this.asxMap = new HashMap<String, String>();
        this.aviMap = new HashMap<String, String>();
        this.flvlvMap = new HashMap<String, String>();
        this.m4vMap = new HashMap<String, String>();
        this.movMap = new HashMap<String, String>();
        this.mp4Map = new HashMap<String, String>();
        this.mpgMap = new HashMap<String, String>();
        this.rmMap = new HashMap<String, String>();
        this.srtMap = new HashMap<String, String>();
        this.swfMap = new HashMap<String, String>();
        this.vobMap = new HashMap<String, String>();
        this.wmvMap = new HashMap<String, String>();


        this.d3mMap = new HashMap<String, String>();
        this.d3sMap = new HashMap<String, String>();
        this.maxMap = new HashMap<String, String>();
        this.objMap = new HashMap<String, String>();

        this.bmpMap = new HashMap<String, String>();
        this.ddaMap = new HashMap<String, String>();
        this.gifMap = new HashMap<String, String>();
        this.jpgMap = new HashMap<String, String>();
        this.pngMap = new HashMap<String, String>();
        this.psdMap = new HashMap<String, String>();
        this.pspimageMap = new HashMap<String, String>();
        this.tgaMap = new HashMap<String, String>();
        this.thmMap = new HashMap<String, String>();
        this.tifMap = new HashMap<String, String>();
        this.tiffMap = new HashMap<String, String>();
        this.yuvMap = new HashMap<String, String>();


        this.aiMap = new HashMap<String, String>();
        this.epsMap = new HashMap<String, String>();
        this.psMap = new HashMap<String, String>();
        this.svgMap = new HashMap<String, String>();


        this.inddMap = new HashMap<String, String>();
        this.pctMap = new HashMap<String, String>();
        this.pdfMap = new HashMap<String, String>();

        this.xlrMap = new HashMap<String, String>();
        this.xlsMap = new HashMap<String, String>();
        this.xlsxMap = new HashMap<String, String>();


        this.z7Map = new HashMap<String, String>();
        this.rarMap = new HashMap<String, String>();
        this.zipMap = new HashMap<String, String>();
        this.targzMap = new HashMap<String, String>();


        this.apkMap = new HashMap<String, String>();
        this.appMap = new HashMap<String, String>();
        this.comMap = new HashMap<String, String>();
        this.exeMap = new HashMap<String, String>();


        this.aspMap = new HashMap<String, String>();
        this.aspxMap = new HashMap<String, String>();
        this.cssMap = new HashMap<String, String>();
        this.htmMap = new HashMap<String, String>();
        this.htmlMap = new HashMap<String, String>();
        this.jsMap = new HashMap<String, String>();
        this.jspMap = new HashMap<String, String>();
        this.phpMap = new HashMap<String, String>();
        this.xhtmlMap = new HashMap<String, String>();

        this.fntMap = new HashMap<String, String>();
        this.fontMap = new HashMap<String, String>();
        this.oftMap = new HashMap<String, String>();
        this.ttfMap = new HashMap<String, String>();


        this.crdownloadMap = new HashMap<String, String>();
        this.icsMap = new HashMap<String, String>();
        this.msiMap = new HashMap<String, String>();
        this.partMap = new HashMap<String, String>();
        this.torrentMap = new HashMap<String, String>();

        this.containsFilters = new TreeMap<String, HashMap<String, String>>();
        this.PathtoFind = PathtoFind;
        this.AddContainFilter();
        this.SavAllFilters();
    }

    private void AddContainFilter() {

        containsFilters.put(DOC, docMap);
        containsFilters.put(DOCX, docxMap);
        containsFilters.put(LOG, logMap);
        containsFilters.put(MSG, msgMap);
        containsFilters.put(ODT, odtMap);
        containsFilters.put(PAGES, pagesMap);
        containsFilters.put(RTF, rtfMap);
        containsFilters.put(TEX, texMap);
        containsFilters.put(TXT, txtMap);
        containsFilters.put(WPD, wpdMap);
        containsFilters.put(WPS, wpsMap);
        containsFilters.put(HWP, hwpMap);

        // Data files
        containsFilters.put(CSV, csvMap);
        containsFilters.put(DAT, datMap);
        containsFilters.put(GBR, gbrMap);
        containsFilters.put(GED, gedMap);
        containsFilters.put(KEY, keyMap);
        containsFilters.put(KEYCHAIN, keychainMap);
        containsFilters.put(PPS, ppsMap);
        containsFilters.put(PPT, pptMap);
        containsFilters.put(PPTX, pptxMap);
        containsFilters.put(SDF, sdfMap);
        containsFilters.put(TAR, tarMap);
        containsFilters.put(TAX2012, tax2012Map);
        containsFilters.put(TAX2014, tax2014Map);
        containsFilters.put(VCF, vcfMap);
        containsFilters.put(XML, xmlMap);

        // Audio files
        containsFilters.put(ALF, alfMap);
        containsFilters.put(IFF, iffMap);
        containsFilters.put(M3U, m3uMap);
        containsFilters.put(M4A, m4aMap);
        containsFilters.put(MID, midMap);
        containsFilters.put(MP3, mp3Map);
        containsFilters.put(MPA, mpaMap);
        containsFilters.put(RA, raMap);
        containsFilters.put(WAV, wavMap);
        containsFilters.put(WMA, wmaMap);

        // Video files
        containsFilters.put(G32, g2Map);
        containsFilters.put(G3P, gpMap);
        containsFilters.put(ASF, asfMap);
        containsFilters.put(ASX, asxMap);
        containsFilters.put(AVI, aviMap);
        containsFilters.put(FLV, flvlvMap);
        containsFilters.put(M4A, m4vMap);
        containsFilters.put(MOV, movMap);
        containsFilters.put(MP4, mp4Map);
        containsFilters.put(MPG, mpgMap);
        containsFilters.put(RM, rmMap);
        containsFilters.put(SRT, srtMap);
        containsFilters.put(SWF, swfMap);
        containsFilters.put(VOB, vobMap);
        containsFilters.put(WMV, wmvMap);


        // 3D image files
        containsFilters.put(D3M, d3mMap);
        containsFilters.put(D3S, d3sMap);
        containsFilters.put(MAX, maxMap);
        containsFilters.put(OBJ, objMap);


        // Raster image files
        containsFilters.put(BMP, bmpMap);
        containsFilters.put(DDA, ddaMap);
        containsFilters.put(GIF, gifMap);
        containsFilters.put(JPG, jpgMap);
        containsFilters.put(PNG, pngMap);
        containsFilters.put(PSD, psdMap);
        containsFilters.put(PSIMAGE, pspimageMap);
        containsFilters.put(TGA, tgaMap);
        containsFilters.put(THM, thmMap);
        containsFilters.put(TIF, tifMap);
        containsFilters.put(TIFF, tiffMap);
        containsFilters.put(YUV, yuvMap);


        // Vector image files
        containsFilters.put(AI, aiMap);
        containsFilters.put(EPS, epsMap);
        containsFilters.put(PS, psMap);
        containsFilters.put(SVG, svgMap);

        // Page layout files
        containsFilters.put(INDD, inddMap);
        containsFilters.put(PCT, pctMap);
        containsFilters.put(PDF, pdfMap);


        // Spreadsheet files
        containsFilters.put(XLR, xlrMap);
        containsFilters.put(XLS, xlsMap);
        containsFilters.put(XLSX, xlsxMap);


        // Compressed files
        containsFilters.put(Z7, z7Map);
        containsFilters.put(RAR, rarMap);
        containsFilters.put(ZIP, zipMap);
        containsFilters.put(TARGZ, targzMap);


        // Executable files
        containsFilters.put(APK, apkMap);
        containsFilters.put(APP, appMap);
        containsFilters.put(COM, comMap);
        containsFilters.put(EXE, exeMap);


        // Web files
        containsFilters.put(ASP, aspMap);
        containsFilters.put(ASPX, aspxMap);
        containsFilters.put(CSS, cssMap);
        containsFilters.put(HTM, htmMap);
        containsFilters.put(HTML, htmlMap);
        containsFilters.put(JS, jsMap);
        containsFilters.put(JSP, jspMap);
        containsFilters.put(PHP, phpMap);
        containsFilters.put(XHTML, xhtmlMap);

        // Font files
        containsFilters.put(FNT, fntMap);
        containsFilters.put(FONT, fontMap);
        containsFilters.put(OFT, oftMap);
        containsFilters.put(TTF, ttfMap);

        // Mick files
        containsFilters.put(CRDOWNLAOD, crdownloadMap);
        containsFilters.put(ICS, icsMap);
        containsFilters.put(MSI, msiMap);
        containsFilters.put(PART, partMap);
        containsFilters.put(TORRENT, torrentMap);


    }


    private void SavAllFilters() {
        try (Stream<Path> paths = Files.walk(Paths.get(PathtoFind))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    System.out.println(filePath);
                    String extendsion = FilenameUtils.getExtension(filePath.toString());
                    String FilePath = FilenameUtils.removeExtension(filePath.toString());
                    SaveToMap(extendsion, FilePath);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SaveToMap(String extension, String FilePath) {
        switch (extension) {
            case DOC:
                docMap.put(FilePath, extension);
                break;
            case DOCX:
                docxMap.put(FilePath, extension);
                break;
            case LOG:
                logMap.put(FilePath, extension);
                break;
            case MSG:
                msgMap.put(FilePath, extension);
                break;
            case ODT:
                odtMap.put(FilePath, extension);
                break;
            case PAGES:
                pagesMap.put(FilePath, extension);
                break;
            case RTF:
                rtfMap.put(FilePath, extension);
                break;
            case TEX:
                texMap.put(FilePath, extension);
                break;
            case TXT:
                txtMap.put(FilePath, extension);
                break;
            case WPD:
                wpdMap.put(FilePath, extension);
                break;
            case WPS:
                wpsMap.put(FilePath, extension);
                break;
            case HWP:
                hwpMap.put(FilePath, extension);
                break;
            case CSV:
                csvMap.put(FilePath, extension);
                break;
            case DAT:
                datMap.put(FilePath, extension);
                break;
            case GBR:
                gbrMap.put(FilePath, extension);
                break;
            case GED:
                gedMap.put(FilePath, extension);
                break;
            case KEY:
                keyMap.put(FilePath, extension);
                break;
            case KEYCHAIN:
                keychainMap.put(FilePath, extension);
                break;
            case PPS:
                ppsMap.put(FilePath, extension);
                break;
            case PPT:
                pptMap.put(FilePath, extension);
                break;
            case PPTX:
                pptxMap.put(FilePath, extension);
                break;
            case SDF:
                sdfMap.put(FilePath, extension);
                break;
            case TAR:
                tarMap.put(FilePath, extension);
                break;
            case TAX2012:
                tax2012Map.put(FilePath, extension);
                break;
            case TAX2014:
                tax2014Map.put(FilePath, extension);
                break;
            case VCF:
                vcfMap.put(FilePath, extension);
                break;
            case XML:
                xmlMap.put(FilePath, extension);
                break;
            case ALF:
                alfMap.put(FilePath, extension);
                break;
            case IFF:
                iffMap.put(FilePath, extension);
                break;
            case M3U:
                m3uMap.put(FilePath, extension);
                break;
            case M4A:
                m4aMap.put(FilePath, extension);
                break;
            case MID:
                midMap.put(FilePath, extension);
                break;
            case MP3:
                mp3Map.put(FilePath, extension);
                break;
            case MPA:
                mpaMap.put(FilePath, extension);
                break;
            case RA:
                raMap.put(FilePath, extension);
                break;
            case WAV:
                wavMap.put(FilePath, extension);
                break;
            case WMA:
                wmaMap.put(FilePath, extension);
                break;
            case G32:
                g2Map.put(FilePath, extension);
                break;
            case G3P:
                gpMap.put(FilePath, extension);
                break;
            case ASF:
                asfMap.put(FilePath, extension);
                break;
            case ASX:
                asxMap.put(FilePath, extension);
                break;
            case AVI:
                aviMap.put(FilePath, extension);
                break;
            case FLV:
                flvlvMap.put(FilePath, extension);
                break;
            case M4V:
                m4vMap.put(FilePath, extension);
                break;
            case MOV:
                movMap.put(FilePath, extension);
                break;
            case MP4:
                mp4Map.put(FilePath, extension);
                break;
            case MPG:
                mpgMap.put(FilePath, extension);
                break;
            case RM:
                rmMap.put(FilePath, extension);
                break;
            case SRT:
                srtMap.put(FilePath, extension);
                break;
            case SWF:
                swfMap.put(FilePath, extension);
                break;
            case VOB:
                vobMap.put(FilePath, extension);
                break;
            case WMV:
                wmvMap.put(FilePath, extension);
                break;
            case D3M:
                d3mMap.put(FilePath, extension);
                break;
            case D3S:
                d3sMap.put(FilePath, extension);
                break;
            case MAX:
                maxMap.put(FilePath, extension);
                break;
            case OBJ:
                objMap.put(FilePath, extension);
                break;
            case BMP:
                bmpMap.put(FilePath, extension);
                break;
            case DDA:
                ddaMap.put(FilePath, extension);
                break;
            case GIF:
                gifMap.put(FilePath, extension);
                break;
            case JPG:
                jpgMap.put(FilePath, extension);
                break;
            case PNG:
                pngMap.put(FilePath, extension);
                break;
            case PSD:
                psdMap.put(FilePath, extension);
                break;
            case PSIMAGE:
                psdMap.put(FilePath, extension);
                break;
            case TGA:
                tgaMap.put(FilePath, extension);
                break;
            case THM:
                thmMap.put(FilePath, extension);
                break;
            case TIF:
                tifMap.put(FilePath, extension);
                break;
            case TIFF:
                tiffMap.put(FilePath, extension);
                break;
            case YUV:
                yuvMap.put(FilePath, extension);
                break;
            case AI:
                aiMap.put(FilePath, extension);
                break;
            case EPS:
                epsMap.put(FilePath, extension);
                break;
            case PS:
                psMap.put(FilePath, extension);
                break;
            case SVG:
                svgMap.put(FilePath, extension);
                break;
            case INDD:
                inddMap.put(FilePath, extension);
                break;
            case PCT:
                pctMap.put(FilePath, extension);
                break;
            case PDF:
                pdfMap.put(FilePath, extension);
                break;
            case XLR:
                xlrMap.put(FilePath, extension);
                break;
            case XLS:
                xlsMap.put(FilePath, extension);
                break;
            case XLSX:
                xlsxMap.put(FilePath, extension);
                break;
            case Z7:
                z7Map.put(FilePath, extension);
                break;
            case RAR:
                rarMap.put(FilePath, extension);
                break;
            case ZIP:
                zipMap.put(FilePath, extension);
                break;
            case TARGZ:
                targzMap.put(FilePath, extension);
                break;
            case APK:
                apkMap.put(FilePath, extension);
                break;
            case APP:
                appMap.put(FilePath, extension);
                break;
            case COM:
                comMap.put(FilePath, extension);
                break;
            case EXE:
                exeMap.put(FilePath, extension);
                break;
            case ASP:
                aspMap.put(FilePath, extension);
                break;
            case ASPX:
                aspxMap.put(FilePath, extension);
                break;
            case CSS:
                cssMap.put(FilePath, extension);
                break;
            case HTM:
                htmMap.put(FilePath, extension);
                break;
            case HTML:
                htmlMap.put(FilePath, extension);
                break;
            case JS:
                jsMap.put(FilePath, extension);
                break;
            case JSP:
                jspMap.put(FilePath, extension);
                break;
            case PHP:
                phpMap.put(FilePath, extension);
                break;
            case XHTML:
                xhtmlMap.put(FilePath, extension);
                break;
            case FNT:
                fntMap.put(FilePath, extension);
                break;
            case FONT:
                fontMap.put(FilePath, extension);
                break;
            case OFT:
                oftMap.put(FilePath, extension);
                break;
            case TTF:
                ttfMap.put(FilePath, extension);
                break;
            case CRDOWNLAOD:
                crdownloadMap.put(FilePath, extension);
                break;
            case ICS:
                icsMap.put(FilePath, extension);
                break;
            case MSI:
                msiMap.put(FilePath, extension);
                break;
            case PART:
                partMap.put(FilePath, extension);
                break;
            case TORRENT:
                torrentMap.put(FilePath, extension);
                break;
            default:
                System.out.println("Error " + FilePath + extension);
                break;

        }
    }

    public TreeMap<String, HashMap<String, String>> GetFileMap() {
        return containsFilters;
    }

}
