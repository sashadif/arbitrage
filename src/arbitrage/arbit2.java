/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbitrage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ю
 */
public class arbit2 {
    
    public static void main(String[] args) {
        
        String answer;
        do {
            System.out.println("Please,input name of file with exchange rates:");
            Scanner in = new Scanner(System.in);
            String filename = in.nextLine();
            solve(filename);
            System.out.println("Press 'y' if you wanna try another file. Or press any other key to exit.");
            answer = in.nextLine();
        } while("y".equals(answer));
    }
    
    public static void solve(String file){    
        int n; //number of vertex
        int m; //number of edges

        ArrayList<Edge> edges = new ArrayList<>();
        try {
            edges = readData(file);  //fill edges with data from file
        } catch (IOException ex) {
            System.out.println("File not found!");
        }

        m = edges.size();
        n = 0;
        for (Edge e : edges) { //count number of vertex
            if (n < e.u) {
                n = e.u;
            }
        }
        n++;
        System.out.println("Created graph:\nnumber of vertex n =" + n + ", number of edges m= " + m);
        
        // Belman-Ford
        double[] dist = new double[n];  //array of distanses
        int[] path = new int[n];  //array with path

        for (int i = 0; i < n; i++) {
            dist[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            path[i] = -1;
        }

        int x = 0;
        for (int i = 0; i < n; i++) {
            x = -1;
            for (int j = 0; j < m; j++) {
                if (dist[edges.get(j).v] > dist[edges.get(j).u] + edges.get(j).weight) {
                    dist[edges.get(j).v] = dist[edges.get(j).u] + edges.get(j).weight;
                    path[edges.get(j).v] = edges.get(j).u;
                    x = edges.get(j).v;
                }
            }
        }
        //search for negative cycle
        if (x != -1) {
            int y = x;
            for (int i = 0; i < n; i++) {
                y = path[y];
            }
            ArrayList<Integer> way = new ArrayList<>();
            for (int cur = y; ; cur = path[cur]) {
                way.add(cur);  //cycle path
                if (cur == y && way.size() > 1) {
                    break;
                }
            }
            Collections.reverse(way);

            System.out.println("Negative cycle:");
            for (Integer i : way) {
                System.out.println(i + " ");
            }
        } else {
            System.out.println("We have a bad news...It seems like there is no negative cycle in the created graph.");
        }
    }

    //read data from file
    private static ArrayList<Edge> readData(String name) throws IOException {
        
        ArrayList<Edge> edges = new ArrayList<>();
        BufferedReader in;

        in = new BufferedReader(new FileReader(name));

        String s = in.readLine();
        while (s != null) {

            StringTokenizer token = new StringTokenizer(s);

            while (token.hasMoreTokens()) {
                int start = Integer.parseInt(token.nextToken());
                int finish = Integer.parseInt(token.nextToken());
                double cost = Double.parseDouble(token.nextToken());
                edges.add(new Edge(start, finish, Math.log(cost) / Math.log(0.5))); //creating edges
            }
            s = in.readLine();
        }
        in.close();
        return edges;
    }
}
