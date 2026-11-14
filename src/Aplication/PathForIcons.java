package Aplication;

import java.util.Map;


public class PathForIcons {

	private static 	Map<String, String> globalTextVariableMap = ReadFileBGTextVariable.getGlobalTextVariableMap();
	
	private static final String destinationIcons = globalTextVariableMap.get("destinationIcons");
	private static final String modifiIcon = globalTextVariableMap.get("modifiIcon");
	private static final String addIcon = globalTextVariableMap.get("addIcon");
	private static final String SaveIcon = globalTextVariableMap.get("SaveIcon");
	private static final String OpenIcon = globalTextVariableMap.get("OpenIcon");
	private static final String winIcon = globalTextVariableMap.get("winIcon");
	private static final String ajaxLoader = globalTextVariableMap.get("ajaxLoader");
	
	
	
	public static String get_destination_addIcon() {
		return destinationIcons+ addIcon;
		 
	}
	
		
	public static String get_destination_SaveIcon() {
		return destinationIcons+ SaveIcon;
	}
	
	public static String get_destination_OpenIcon() {
		return destinationIcons+ OpenIcon;
	}
	
	public static String get_destination_winIcon() {
		return destinationIcons+ winIcon;
	}
	public static String get_destination_ajaxLoader() {
		return  destinationIcons+ ajaxLoader;
	}
	public static String get_destination_ModifyIcon() {
		return destinationIcons+ modifiIcon;
	}	
	
}
