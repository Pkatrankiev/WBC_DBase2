package Aplication;

import java.util.List;

import BasicClassAccessDbase.Measuring;

public class ReportMeasurClass  {
	 
		private Measuring measur;
		private List<String> listNuclideData;
		private boolean toExcell;
		private String koment;
		
		public ReportMeasurClass (Measuring measur, List<String> listNuclideData, boolean toExcell, String koment) 
		{
			this.measur = measur;
			this.listNuclideData = listNuclideData;
			this.toExcell = toExcell;
			this.koment = koment;
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

		

	}