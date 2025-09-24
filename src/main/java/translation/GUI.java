package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            // Get all country codes
            JSONTranslator jsonTranslator = new JSONTranslator();
            List<String> countryCodes = jsonTranslator.getCountryCodes();
            // convert country codes to country name
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            // create a list of all countries.
            List<String> countries = new ArrayList<>();
            for (String countryCode : countryCodes) {
                countries.add(countryCodeConverter.fromCountryCode(countryCode));
            }
            // create a drop down menu and add all countries to the dropdown
            JComboBox comboBox = new JComboBox();
            for (String country : countries) {
                comboBox.addItem(country);
            }
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(comboBox);

            // create a JList
            JPanel languagePanel = new JPanel();
            // get all language codes
            List<String> languageCodes = jsonTranslator.getLanguageCodes();
            // convert language codes to language names
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            // create a list of all languages
            List<String> languages = new ArrayList<>();
            for (String languageCode : languageCodes) {
                languages.add(languageCodeConverter.fromLanguageCode(languageCode));
            }
            JList<String> jlist = new JList<>(languages.toArray(new String[0]));
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(jlist);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String country = (String) comboBox.getSelectedItem();
                    String language = jlist.getSelectedValue();
                    System.out.println(country);
                    System.out.println(language);

                    // country is the 3 digit country code.
                    CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
                    String countryCode = countryCodeConverter.fromCountry(country);

                    LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
                    String languageCode = languageCodeConverter.fromLanguage(language);

                    System.out.println(countryCode);
                    System.out.println(languageCode);

                    JSONTranslator jsonTranslator = new JSONTranslator();

                    String result = jsonTranslator.translate(countryCode, languageCode);

                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
