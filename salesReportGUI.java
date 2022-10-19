
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

/**
 * This class holds the main functionality behind the sales report GUI logic.
 */

public class salesReportGUI {
    public JFrame frame;

    private JPanel dataView;
    private JPanel dataViewContainer;
    private JScrollPane scrollPane;
    private JTextArea salesStats;

    private String startDate;
    private String endDate;

    private JTextArea startDateArea;
    private JTextArea endDateArea;

    private JTextArea excessDateArea;

    jdbcpostgreSQL2 jd = new jdbcpostgreSQL2();
    String allSales;

    /**
     * Constructor for the salesReportGUI object
     * 
     * @param
     * @return
     * @throws
     */
    public salesReportGUI() {
        frame = new JFrame();
        frame.setTitle("Sales Data");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(800, 800));

        dataPickerGUI();
        excessReportGUI();
        restockReportGUI();

        dataView = new JPanel(new GridLayout(1, 1));
        dataViewContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        dataViewerGUI();
        frame.getContentPane().add(scrollPane);
        frame.setVisible(true);
    }

    /**
     * The dataViewerGUI function creates a GUI for the dataViewer.
     * 
     * 
     *
     * @param
     * @return
     * @throws
     */
    public void dataViewerGUI() {
        salesStats = new JTextArea();
        salesStats.setEditable(false);
        dataView.add(salesStats);
        dataViewContainer.add(dataView);
        scrollPane = new JScrollPane(dataViewContainer);
        dataView.setBackground(Color.BLACK);
        scrollPane.setBounds(200, 110, 500, 625);

    }

    /**
     * The dataPickerGUI function creates a GUI that allows the user to enter two
     * dates and then displays sales between those dates.
     * 
     *
     * @param
     * @return The startdate and enddate variables
     * @throws
     * 
     */
    public void dataPickerGUI() {
        // "MM/dd/yyyy HH:mm"
        JLabel instructions = new JLabel("Enter Start MM/dd/yyyy");
        instructions.setBounds(200, 10, 200, 30);
        frame.add(instructions);

        startDateArea = new JTextArea();
        startDateArea.setBounds(200, 30, 100, 30);
        frame.add(startDateArea);

        JLabel instructions2 = new JLabel("Enter End MM/dd/yyyy");
        instructions2.setBounds(450, 10, 200, 30);
        frame.add(instructions2);

        endDateArea = new JTextArea();
        endDateArea.setBounds(450, 30, 100, 30);
        frame.add(endDateArea);

        JButton showSales = new JButton("Show Sales");
        showSales.setBounds(600, 20, 150, 30);
        frame.add(showSales);

        showSales.addActionListener(e -> {
            startDate = startDateArea.getText();
            endDate = endDateArea.getText();
            salesStats.setText(displaySalesBetweenDates());
        });
    }

    /**
     * The displaySalesBetweenDates function displays the sales between two dates.
     * 
     *
     * @param
     * @return A string that contains all of the sales that occurred between the two
     *         dates entered by the user
     * @throws
     */
    public String displaySalesBetweenDates() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String holder = "";

        try {
            Date startDateObject = formatter.parse(startDate);

            Date endDateObject = formatter.parse(endDate);
            long addDay = endDateObject.getTime() + 24 * 60 * 60 * 1000;
            endDateObject = new Date(addDay);

            while (startDateObject.compareTo(endDateObject) < 0) {

                String currentDate = formatter2.format(startDateObject);
                System.out.println(currentDate);
                holder += jd.viewSales(currentDate);
                long addDay2 = startDateObject.getTime() + 24 * 60 * 60 * 1000;
                startDateObject = new Date(addDay2);
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();

        }

        System.out.println(holder);
        return holder;
    }

    /**
     * The excessReportGUI function creates a GUI that allows the user to enter an
     * excess date and then displays all of the ingredients that are over 10% in
     * quantity on hand.
     * 
     *
     * @param
     * @return The keys of the ingredients that are in excess
     * @throws
     * 
     */
    public void excessReportGUI() {

        JLabel excessLabel = new JLabel("Enter excess MM/dd/yyyy");
        excessLabel.setBounds(20, 100, 200, 30);
        frame.add(excessLabel);

        excessDateArea = new JTextArea();
        excessDateArea.setBounds(30, 150, 100, 30);
        frame.add(excessDateArea);

        JButton excessButton = new JButton("<html>" + "Show Excess" + "<br/>" + "Report" + "</html>");
        excessButton.setBounds(20, 200, 150, 50);
        frame.add(excessButton);
        excessButton.addActionListener(e -> {
            String keyHolder = "";

            System.out.println(jd.viewTenPercentIngredients());
            String[] ingredients = jd.viewTenPercentIngredients().split(",");
            System.out.println(ingredients[0]);
            for (Map.Entry<String, String> iMap : jdbcpostgreSQL2.sg.ingredientMap.entrySet()) {
                String key = iMap.getKey();
                String ing = iMap.getValue();

                for (int i = 0; i < ingredients.length; i++) {
                    String compare = "";
                    // System.out.println(ing + " " + ingredients[i].trim());
                    for (int j = 0; j < ingredients[i].length(); j++) {
                        if (Character.isLetter(ingredients[i].charAt(j))) {
                            compare += ingredients[i].charAt(j);
                        }
                    }
                    System.out.println("ORIGNAL" + compare);
                    if (ing.indexOf(compare) != -1 && ing.indexOf(compare) != 0) {
                        System.out.println(ing + ing.indexOf(compare) + "MATCH" + compare);

                        keyHolder += key + "\n";
                    }
                }

            }
            System.out.println("keys:" + keyHolder);
            salesStats.setText(keyHolder);
        });

    }

    /**
     * The restockReportGUI function creates a GUI that displays the restock report.
     * 
     *
     * @param
     * @return A jbutton that displays the restock report when clicked
     * @throws
     */
    public void restockReportGUI() {
        JButton excessButton = new JButton("<html>" + "Show Restock" + "<br/>" + "Report" + "</html>");
        excessButton.setBounds(20, 350, 150, 50);
        frame.add(excessButton);

        excessButton.addActionListener(e -> {
            salesStats.setText(jd.viewRestock());
        });

    }
}
