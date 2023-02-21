package SearchFreeKode;

import java.util.ArrayList;
import java.util.List;

import Aplication.RemouveDublikateFromList;
import BasiClassDAO.KodeGenerateDAO;
import BasicClassAccessDbase.KodeGenerate;


public class SearchFreeKodeMethods {

	public static List<String> getMasiveZvenaFromDBase() {
		List<String> list = new ArrayList<>();
		List<KodeGenerate> listKG = KodeGenerateDAO.getAllValueKodeGenerate();
		for (KodeGenerate kodeGenerate : listKG) {
			list.add(kodeGenerate.getWorkplace().getOtdel());
		}
		return RemouveDublikateFromList.removeDuplicates(new ArrayList<String>(list));
	}

}
