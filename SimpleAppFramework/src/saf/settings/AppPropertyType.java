package saf.settings;

/**
 * This enum provides properties that are to be loaded via
 * XML files to be used for setting up the application.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public enum AppPropertyType {
        // LOADED FROM simple_app_properties.xml
    
    //stuff for HW4
    //BUTTONS 
    RENAME_MAP,
    ADD_IMAGE,
    REMOVE,
    CHANGE_BACKGROUND_COLOR,
    CHANGE_BORDER_COLOR,
    RANDOMIZE_MAP_COLORS,
    CHANGE_MAP_DIMENSIONS,
    PLAY_ANTHEM,
    PAUSE_ANTHEM,
    NEXT_BUTTON,
    PREV_BUTTON,
    PARENT_DIRECTORY_BUTTON,
    DATA_FILE_BUTTON,
    //BUTTON TOOLTIPS
    RENAME_MAP_TT,
    ADD_IMAGE_TT,
    REMOVE_TT,
    CHANGE_BACKGROUND_COLOR_TT,
    CHANGE_BORDER_COLOR_TT,
    RANDOMIZE_MAP_COLORS_TT,
    CHANGE_MAP_DIMENSIONS_TT,
    PLAY_ANTHEM_TT,
    PAUSE_ANTHEM_TT,
    NEXT_BUTTON_TT,
    PREV_BUTTON_TT,
    PARENT_DIRECTORY_BUTTON_TT,
    DATA_FILE_BUTTON_TT,
    
    //SLIDERS
    CHANGE_BORDER_THICKNESS,
    ZOOM,
    
    
    
    
    /**
     *
     */
            APP_TITLE,

    /**
     *
     */
    APP_LOGO,

    /**
     *
     */
    APP_CSS,

    /**
     *
     */
    APP_PATH_CSS,
        
        // APPLICATION ICONS

    /**
     *
     */
            NEW_ICON,

    /**
     *
     */
    LOAD_ICON,

    /**
     *
     */
    SAVE_ICON,

    /**
     *
     */
    SAVE_AS_ICON,

    /**
     *
     */
    EXIT_ICON,
        
    
    EXPORT_ICON,
    
        // APPLICATION TOOLTIPS FOR BUTTONS

    /**
     *
     */
            NEW_TOOLTIP,

    /**
     *
     */
    LOAD_TOOLTIP,

    /**
     *
     */
    SAVE_TOOLTIP,

    /**
     *
     */
    SAVE_AS_TOOLTIP,

    /**
     *
     */
    EXPORT_TOOLTIP,

    /**
     *
     */
    EXIT_TOOLTIP,
	
	// ERROR MESSAGES

    /**
     *
     */
    	NEW_ERROR_MESSAGE,

    /**
     *
     */
    LOAD_ERROR_MESSAGE,

    /**
     *
     */
    SAVE_ERROR_MESSAGE,

    /**
     *
     */
    PROPERTIES_LOAD_ERROR_MESSAGE,
	
	// ERROR TITLES

    /**
     *
     */
    	NEW_ERROR_TITLE,

    /**
     *
     */
    LOAD_ERROR_TITLE,

    /**
     *
     */
    SAVE_ERROR_TITLE,

    /**
     *
     */
    PROPERTIES_LOAD_ERROR_TITLE,
	
	// AND VERIFICATION MESSAGES AND TITLES

    /**
     *
     */
            NEW_COMPLETED_MESSAGE,

    /**
     *
     */
    NEW_COMPLETED_TITLE,

    /**
     *
     */
    LOAD_COMPLETED_MESSAGE,

    /**
     *
     */
    LOAD_COMPLETED_TITLE,

    /**
     *
     */
    SAVE_COMPLETED_MESSAGE,
	
    /**
     *
     */
    SAVE_COMPLETED_TITLE,	

    /**
     *
     */
    SAVE_UNSAVED_WORK_TITLE,

    /**
     *
     */
    SAVE_UNSAVED_WORK_MESSAGE,
	
    /**
     *
     */
    SAVE_WORK_TITLE,

    /**
     *
     */
    LOAD_WORK_TITLE,

    /**
     *
     */
    WORK_FILE_EXT,

    /**
     *
     */
    WORK_FILE_EXT_DESC,

    /**
     *
     */
    PROPERTIES_
}
