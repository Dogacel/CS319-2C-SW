package SevenWonders.userinterface.controller;

import SevenWonders.userinterface.model.SettingsModel;
import SevenWonders.userinterface.view.SettingsView;

public class SettingsController {
    public SettingsModel model;
    public SettingsView view;

    public SettingsController(SettingsModel model)
    {
        this.model = model;
    }
}
