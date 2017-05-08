/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbitrage;

/**
 *
 * @author Ю
 */
public class Edge {
    int u;
    int v;
    double weight;
    
    public Edge(int a, int b,double cost){
        u = a;
        v = b;
        weight = cost;
    }
    
}
