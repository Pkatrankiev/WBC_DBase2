package Aplication;

import java.util.List;

import BasicClassAccessDbase.Measuring;

public class ReportMeasurClass  {
	 
		private Measuring measur;
		private List<String> listNuclideData;
		
		public ReportMeasurClass (Measuring measur, List<String> listNuclideData) 
		{
			this.measur = measur;
			this.listNuclideData = listNuclideData;
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

		

	}