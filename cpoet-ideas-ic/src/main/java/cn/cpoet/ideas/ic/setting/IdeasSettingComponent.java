package cn.cpoet.ideas.ic.setting;

import cn.cpoet.ideas.ic.component.CustomComboBox;
import cn.cpoet.ideas.ic.constant.LanguageEnum;
import cn.cpoet.ideas.ic.util.I18nUtil;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.util.stream.Stream;

/**
 * 插件配置组件
 *
 * @author CPoet
 */
public class IdeasSettingComponent {

    private final JPanel mainPanel;

    private final ComboBox<LanguageEnum> selectLanguageComboBox;

    public IdeasSettingComponent() {
        selectLanguageComboBox = buildSelectLanguageComboBox();
        mainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(I18nUtil.t("settings.SelectLanguage.label"), selectLanguageComboBox)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    private ComboBox<LanguageEnum> buildSelectLanguageComboBox() {
        CustomComboBox<LanguageEnum> comboBox = new CustomComboBox<>();
        comboBox.customText(LanguageEnum::getName);
        Stream.of(LanguageEnum.values()).forEach(comboBox::addItem);
        for (LanguageEnum value : LanguageEnum.values()) {
            comboBox.addItem(value);
        }
        return comboBox;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public LanguageEnum getLanguage() {
        return (LanguageEnum) selectLanguageComboBox.getSelectedItem();
    }

    public void setLanguage(LanguageEnum language) {
        selectLanguageComboBox.setSelectedItem(language);
    }
}
