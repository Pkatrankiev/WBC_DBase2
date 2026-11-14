package Aplication;

import java.io.File;
import java.util.List;

import BasicClassAccessDbase.Measuring;

public class ReportMeasurClass  {
	 
		private Measuring measur;
		private List<String> listNuclideData;
		private boolean toExcell;
		private String koment;
		private File reportFile;
		
		public ReportMeasurClass (Measuring measur, 
				List<String> listNuclideData, 
				boolean toExcell, 
				String koment,
				File reportFile) 
		{
			this.measur = measur;
			this.listNuclideData = listNuclideData;
			this.toExcell = toExcell;
			this.koment = koment;
			this.reportFile = reportFile;
		}
		
		public boolean getToExcell() {
			return toExcell;
		}

		public void setToExcell(boolean toExcell) {
			this.toExcell = toExcell;
		}

		public List<String> getListNuclideData() {
			return listNuclideData;
		}

		public void setListNuclideData(List<String> listNuclideData) {
			this.listNuclideData = listNuclideData;
		}

		public ReportMeasurClass() {
			super();
		}

		public Measuring getMeasur() {
			return measur;
		}

		public void setMeasur(Measuring measur) {
			this.measur = measur;
		}

		public String getKoment() {
			return koment;
		}

		public void setKoment(String koment) {
			this.koment = koment;
		}

		public File getReportFile() {
			return reportFile;
		}

		public void setReportFile(File reportFile) {
			this.reportFile = reportFile;
		}

	}