package PersonReference_OID;

import java.text.SimpleDateFormat;
import java.util.List;

import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Workplace;

public class OID_Metods {

	
	public static String getInfoFromAECByEGN(String egn) {

		String str = "";
		OID_Person_AEC person_AEC = OID_Person_AECDAO.getOID_Person_AECByEGN(egn);
		if (person_AEC != null) {
			str += person_AEC.getEgn() + "   " + person_AEC.getFirstName() + " "
					+ person_AEC.getSecondName() + " " + person_AEC.getLastName() + "  " + person_AEC.getDateSet()+"\n";

			int org_str = OID_Person_AECDAO.get_ORG_STR_ID_By_Oid_Person_AEC_ID(person_AEC.getOid_Person_AEC_ID());

			int dep = OID_Person_AECDAO.get_DEP_ID_By_ORG_STR_ID(org_str);

			String posName = OID_Person_AECDAO.get_OS_POS_NAME_By_ORG_STR_ID(org_str);
			str += posName +"\n";
			
			String d_hCode = OID_Person_AECDAO.get_D_HCODE_By_DEP_ID(dep);

			String d_Code = OID_Person_AECDAO.get_DEPT_CODE_By_DEP_ID(dep);
			System.out.println(d_Code);
			String otdel = getOtdelByWorkplace(getWorkplaceByDeptCode(d_Code));
			str += d_Code +" ("+ otdel+")\n";
			String department = "";
			for (int i = 0; i < d_hCode.length(); i += 6) {
				int count = Integer.parseInt(d_hCode.substring(i, i + 6));
				department += OID_Person_AECDAO.get_D_Name_By_DEP_ID(count) + ",\n";
			}
			if(department.length()>3) {
			department = department.substring(0, department.length() - 3);
			}
			
			str += department+ "\n";
		}
		return str;

	}

	public static String getOtdelByWorkplace(Workplace workpl) {
			if(workpl != null) {
				return workpl.getOtdel();
		}
		return "??";
	}
	
	public static Workplace getWorkplaceByDeptCode(String d_Code) {
		String CodeOtdel;
		List<Workplace> listWorkPlace = WorkplaceDAO.getAllActualValueWorkplace();
		for (Workplace workplace : listWorkPlace) {
			CodeOtdel = workplace.getNapOtdelSector();
			System.out.println(d_Code+" - "+ CodeOtdel);
			if(!CodeOtdel.isEmpty()  && d_Code.contains(CodeOtdel)) {
				return workplace;
			}
		}
		return null;
	}
	
	
	
	public static String getDeptCodeByEGN(String egn) {
		String str = "";
		OID_Person_AEC person_AEC = OID_Person_AECDAO.getOID_Person_AECByEGN(egn);
		if (person_AEC != null) {

			int org_str = OID_Person_AECDAO.get_ORG_STR_ID_By_Oid_Person_AEC_ID(person_AEC.getOid_Person_AEC_ID());

			int dep = OID_Person_AECDAO.get_DEP_ID_By_ORG_STR_ID(org_str);

			str = OID_Person_AECDAO.get_DEPT_CODE_By_DEP_ID(dep);
		}
		return str;
	}
	
	
	
	public static String getInfoFromVOByEGN(String egn) {

		String str = "";
		OID_Person_VO person_VO = OID_Person_VODAO.getOID_Person_VOByEGN(egn);
		if (person_VO != null) {

			System.out.println("33333333333333  "+person_VO.getPredID());
			String predpriqtie = OID_Person_VODAO.getPredptiqtieByPred_ID(person_VO.getPredID());

			str = person_VO.getEgn() + " " + person_VO.getFirstName() + " " + person_VO.getSecondName()
					+ " " + person_VO.getLastName() + "  " + predpriqtie+"\n";

		}
		return str;

	}

	public static String getInfoFromWBCByEGN(String egn) {

		String str = "";
		List<OID_Person_WBC> list = OID_Person_WBCDAO.getlist_OID_Person_WBCByEGN(egn);
		if (list != null ) {
			if (list.size() < 2 ) {
				OID_Person_WBC person = list.get(0);
			
				str = person.getEgn() + "   " + person.getFirstName() + " " + person.getSecondName()
					+ " " + person.getLastName()+"\n";
				str += "Код зона 1  " +person.getZsr1() + " Код зона 2  " +person.getZsr2()+"\n";


		}
		}
		return str;
	}
	
	public static String getInfoVlizaniqByEGN(String egn) {

		String str = "";
		SimpleDateFormat sdfrmt = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		List<Vlizaniq> list = VlizaniqDAO.getlist_VlizaniqByEGN(egn);
		if (list != null ) {
			for (Vlizaniq vlizaniq : list) {
			
				str += "На "+sdf.format(vlizaniq.getDateIn())+" в зона "+vlizaniq.getZona()+" от "+sdfrmt.format(vlizaniq.getTimeIn())+" до "+
						sdfrmt.format(vlizaniq.getTimeOut())+" с доза "+vlizaniq.getDoza()+"\n";

			

			}
		}
		return str;
		
	}
	
	public static String getInfoByEGN(String egn) {
		String   str = "\n***************************** От АЕЦ *********************************\n";
		str += OID_Metods.getInfoFromAECByEGN(egn);
				str += "\n*********************** От Външни Организации ************************\n";
		str += OID_Metods.getInfoFromVOByEGN(egn);
				str += "\n*************************** От Персонал АЕЦ **************************\n";
		str += OID_Metods.getInfoFromWBCByEGN(egn);
				str += "\n************************** Влизания в зоните *************************\n";
		str += OID_Metods.getInfoVlizaniqByEGN(egn);
	return str;
}
}
