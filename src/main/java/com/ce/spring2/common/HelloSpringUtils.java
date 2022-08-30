package com.ce.spring2.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloSpringUtils {
	/**
	 * totalPage 전체페이지수
	 * pagebarSize 
	 * pageNo
	 * pagebarStart
	 * pagebarEnd
	 */
	public static String getPagebar(int cPage, int limit, int totalContent, String url) {
		StringBuffer pagebar = new StringBuffer();
		url += "?cPage=";
		
		final int pagebarSize = 5;
		final int totalPage = (int)Math.ceil((double)totalContent / limit);
		final int pagebarStart = ((cPage - 1) / pagebarSize) * pagebarSize + 1;
		final int pagebarEnd = pagebarStart + pagebarSize - 1;
		int pageNo = pagebarStart;
		
		pagebar.append("<ul class=\"pagination justify-content-center\">\n");
		// 1. previous
		if(cPage == 1) {
			pagebar.append("<li class=\"page-item disabled\">\n"
					+ "      <a class=\"page-link\" href=\"#\" aria-label=\"Previous\">\n"
					+ "        <span aria-hidden=\"true\">&laquo;</span>\n"
					+ "        <span class=\"sr-only\">Previous</span>\n"
					+ "      </a>\n"
					+ "    </li>\n");
		} else {
			pagebar.append("<li class=\"page-item\">\n"
					+ "      <a class=\"page-link\" href=\"" + url + (pageNo-1) + "\" aria-label=\"Previous\">\n"
					+ "        <span aria-hidden=\"true\">&laquo;</span>\n"
					+ "        <span class=\"sr-only\">Previous</span>\n"
					+ "      </a>\n"
					+ "    </li>\n");
		}
		
		// 2. pageNo
		while(pageNo <= pagebarEnd && pageNo <= totalPage) {
			if(pageNo == cPage) {
				pagebar.append("<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + pageNo + "</a></li>\n");
			} else {				
				pagebar.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + pageNo +"\">" + pageNo + "</a></li>\n");
			}
			pageNo++;
		}
		
		// 3. next
		if(pageNo > totalPage) {
			pagebar.append("<li class=\"page-item disabled\">\n"
					+ "      <a class=\"page-link\" href=\"#\" aria-label=\"Next\">\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span>\n"
					+ "        <span class=\"sr-only\">Next</span>\n"
					+ "      </a>\r\n"
					+ "    </li>\n");
		} else {
			pagebar.append("<li class=\"page-item\">\n"
					+ "      <a class=\"page-link\" href=\"" + url + pageNo + "\" aria-label=\"Next\">\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span>\n"
					+ "        <span class=\"sr-only\">Next</span>\n"
					+ "      </a>\n"
					+ "    </li>\n");
		}
		pagebar.append("</ul>");
		
		return pagebar.toString();
	}

	public static String getRenamedFilename(String originalFilename) {
		// 확장자 추출
		int beginIndex = originalFilename.lastIndexOf(".");
		String ext = "";
		if(beginIndex > -1) {
			ext = originalFilename.substring(beginIndex); // .txt
		}
		
		// 새이름 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
		DecimalFormat df = new DecimalFormat("000");
		return sdf.format(new Date()) + df.format(Math.random()*1000) + ext;
	}
}
/*
   <ul class="pagination">
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
        <span class="sr-only">Previous</span>
      </a>
    </li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
        <span class="sr-only">Next</span>
      </a>
    </li>
  </ul>
 */
