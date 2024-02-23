//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
package org.example;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class HospitalFinder  implements  ActionListener{
    // Define the data structures needed for the program.
    private Map<String, Point2D.Double> hospitalLocations; // Map of hospital locations
    //private Point2D.Double patientLocation; // Location of patient
    private Map<String, Double> distanceMap; // Map of distances from patient to each hospital
    private JPanel panel; // Panel for displaying the GUI
    private JTextField latitudeTextField;
    private JTextField longitudeTextField;
    private JButton searchButton;


    public HospitalFinder() {

        // Set up the window
        JFrame hello=new JFrame("Input location");
        hello.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hello.setSize(400, 200);

        // Create the input fields
        latitudeTextField = new JTextField(10);
        longitudeTextField = new JTextField(10);


        // Create the search button
        searchButton = new JButton("Look-up");
        searchButton.addActionListener((ActionListener) this);

     // Display the map of the distance to the nearest hospital on a GUI
//        JFrame frame=new JFrame("Kakamega Ambulancce Lookup");
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500, 500);

        // Add the components to the window
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Latitude:"));
        inputPanel.add(latitudeTextField);
        inputPanel.add(new JLabel("Longitude:"));
        inputPanel.add(longitudeTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);

        Container contentPane = hello.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);


        hello.setVisible(true);
    }

    double latitude;
    double longitude;


        public void actionPerformed(ActionEvent e){
            hospitalLocations = new HashMap<>();
            hospitalLocations.put("Kakamega General Hospital", new Point2D.Double(0.2748, 34.7607));
            hospitalLocations.put("Lumino Nursing Home", new Point2D.Double(0.3023, 34.7643));
            hospitalLocations.put("Mmust Medical Center", new Point2D.Double(0.2886, 34.7662));
            hospitalLocations.put("Lupe Medical Center", new Point2D.Double(0.2905, 34.7682));
            hospitalLocations.put("Forest Dispensary", new Point2D.Double(0.2984, 34.8847));
            if (e.getSource() == searchButton) {
                // Get the latitude and longitude from the input fields
                latitude = Double.parseDouble(latitudeTextField.getText());
                longitude = Double.parseDouble(longitudeTextField.getText());

//                panel.repaint();

                // Calculate the distance from the patient to each hospital using the Haversine formula
                final double EARTH_RADIUS = 6371; // Earth's radius in kilometers
                distanceMap = new HashMap<>();
                for (String hospital : hospitalLocations.keySet()) {
                    Point2D location = hospitalLocations.get(hospital);
                    double lat1 = Math.toRadians(latitude);
                    double lon1 = Math.toRadians(longitude);
                    double lat2 = Math.toRadians(location.getX());
                    double lon2 = Math.toRadians(location.getY());
                    double dLat = lat2 - lat1;
                    double dLon = lon2 - lon1;
                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                            Math.cos(lat1) * Math.cos(lat2) *
                                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                    double distance = EARTH_RADIUS * c;
                    if (distance <= 60.0) { // Only add hospitals within 60-km radius to distanceMap
                        distanceMap.put(hospital, distance);
                    }
                }

                //Display the map of the distance to the nearest hospital on a GUI
                JFrame frame=new JFrame("Kakamega Ambulancce Lookup");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 500);
                Container map = frame.getContentPane();
                map.setLayout(new BorderLayout());
                map.setBackground(Color.white);
                frame.setVisible(true);


                // Find the nearest hospital and its distance
                String nearestHospital = "";
                double shortestDistance = Double.MAX_VALUE;
                for (String hospital : distanceMap.keySet()) {
                    double distance = distanceMap.get(hospital);
                    if (distance < shortestDistance) {
                        nearestHospital = hospital;
                        shortestDistance = distance;
                    }
                }

                if (!nearestHospital.equals("")) { // check if a nearest hospital is found within the restricted radius
                    System.out.println("The nearest hospital is " + nearestHospital + " and its distance from the patient is " + shortestDistance + " kilometers.");
                } else {
                    System.out.println("No hospital found within the restricted radius of 60 kilometers.");
                }


                
                
                /*panel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(Color.BLUE);
                        Font font = new Font("Arial", Font.PLAIN, 12); // set the font and size of the text
                        g.setFont(font); // apply the font to the graphics object
                        for (String hospital : hospitalLocations.keySet()) {
                            Point2D location = hospitalLocations.get(hospital);
                            g.drawOval((int) (location.getX() * 1000) - 125, (int) (location.getY() * 1000) + 25, 10, 10);
                            g.drawString(hospital, (int) (location.getX() * 1000) + 15, (int) (location.getY() * 1000) + 15);

                        }
                        g.setColor(Color.RED);
                        for (String hospital : hospitalLocations.keySet()) {
                            Point2D location = hospitalLocations.get(hospital);
                            g.drawLine((int) latitude, (int) longitude, (int) location.getX() - 25, (int) location.getY() + 98);
                            String distanceString = String.format("%.2f km", distanceMap.get(hospital) + 40); // format the distance string to include the units
                            g.drawString(distanceString, (int) ((latitude + location.getX() + 76) / 2),
                                    (int) ((longitude + location.getY() + 120) / 2));
                        }
                        g.setColor(Color.BLACK);
                        g.drawString("Patient", (int) latitude, (int) longitude);
                    }
                };*/


            }


    }

    public static void main(String[] args) {
        HospitalFinder hospitalFinder = new HospitalFinder();

    }
}


