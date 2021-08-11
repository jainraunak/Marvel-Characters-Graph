import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;

public class MarvelCharacters{
  static class Vertex{
	String marvelch;
	int chId;
	public Vertex(String s,int x){
		chId = x;
		marvelch = s;
	}
  }
  static class Edges{
	Vertex adjvertex;
        int weight;
        public Edges(Vertex v,int w){
             weight = w;
             adjvertex = v;
        }
  }
  static class Graph{
	int vertexsize;
	Vertex[] varr;
	ArrayList<Edges>[] adj;
	Boolean[] visited;
	HashMap<String,Integer> hp = new HashMap<String,Integer>();
	public Graph(int n,Vertex[] arr){
		varr = new Vertex[n];
		visited = new Boolean[n];
		for(int i = 0;i<n;i++){
			varr[i] = arr[i];
			visited[i] = false;
			hp.put(arr[i].marvelch,arr[i].chId);
		}
		vertexsize = n;
		adj = new ArrayList[n];
      		for(int i = 0;i<n;i++){
        		adj[i] = new ArrayList<Edges>();
      		}
	}
        public void addEdge(Vertex u,Vertex v,int w){
        	Edges e1 = new Edges(u,w);
        	Edges e2 = new Edges(v,w);
        	adj[u.chId].add(e2);
        	adj[v.chId].add(e1);
        }
  }
  public static void average(Graph g){
	int n = g.vertexsize;
	float sum = 0;
	for(int i = 0;i<n;i++){
		float m = g.adj[i].size();
		sum = sum+m;
	}
        if(n != 0){
		 sum = sum/n;
        }
        String s = String.format("%.2f", sum);
	System.out.println(s);
   }
   public static void merge(Vertex[] arr,int l,int m,int r){
	int n1 = m-l+1;
	Vertex[] a1 = new Vertex[n1];
        for(int i = 0;i < n1;i++){
            a1[i] = arr[i+l];
        }
        int n2 = r-m;
        Vertex[] a2 = new Vertex[n2];
        for(int i = 0;i < n2;i++){
            a2[i] = arr[i+m+1];
        }
        int i = 0;
        int j = 0;
        int p = l;
        while(i < n1 && j < n2){
           if(a1[i].chId < a2[j].chId){
               arr[p] = a2[j];
               j++;
            }
            else if(a1[i].chId > a2[j].chId){
               arr[p] = a1[i];
               i++;
            } 
            else{
               if(a1[i].marvelch.compareTo(a2[j].marvelch) < 0){
                  arr[p] = a2[j];
                  j++;
               }
               else{
                  arr[p] = a1[i];
                  i++;
               }
            }
            p++;
        }
	while(i < n1){
	    arr[p] = a1[i];
	    i++;
	    p++;
	}
	while(j < n2){
	    arr[p] = a2[j];
	    j++;
	    p++;
	}
   }
   public static void msort(Vertex[] arr,int l,int r){
	if(l < r){
		int m = (l+r)/2;
		msort(arr,l,m);
		msort(arr,m+1,r);
      		merge(arr,l,m,r);
	}
    }
    public static void rank(Graph g,Vertex[] arr){
	int n = g.vertexsize;
	for(int i = 0;i < n;i++){
		int sum = 0;
		int s = g.adj[i].size();
		for(int j = 0;j<s;j++){
			sum = sum+g.adj[i].get(j).weight;
		}
		Vertex v = new Vertex(g.varr[i].marvelch,sum);
		arr[i] = v;
	}
        msort(arr,0,n-1);
        for(int i = 0;i<n-1;i++){
        	System.out.print(arr[i].marvelch);
        	System.out.print(",");
        }
        if(n > 0){
           System.out.println(arr[n-1].marvelch);
        }
    }
    public static void dfs(Graph g,ArrayList<String> a,Vertex v){
    	int i = v.chId;
    	g.visited[i] = true;
    	a.add(v.marvelch);
    	int m = g.adj[i].size();
    	for(int j = 0;j < m;j++){
    		String s = g.adj[i].get(j).adjvertex.marvelch;
    		int x = g.hp.get(s);
    		if(g.visited[x] == false){
    			dfs(g,a,g.adj[i].get(j).adjvertex);
    		}
    	}
    }
    public static void sc(Graph g){
    	ArrayList<ArrayList<String>> al = new ArrayList<>();
    	int n = g.vertexsize;
    	for(int i = 0;i<n;i++){
    		if(g.visited[i] == false){
    			ArrayList<String> a = new ArrayList<>();
                dfs(g,a,g.varr[i]);
                al.add(a);
    		}
    	}
    	HashMap<String,Vertex[]> h = new HashMap<String,Vertex[]>();
    	int m = al.size();
    	Vertex[] arr = new Vertex[m];
    	for(int i = 0;i<m;i++){
    		ArrayList<String> l = al.get(i);
    		int d = l.size();
    		Vertex[] t = new Vertex[d];
            	for(int j = 0;j<d;j++){
            		t[j] = new Vertex(l.get(j),d);
            	}
            	msort(t,0,d-1);
            	String s = t[0].marvelch;
            	h.put(s,t);
            	arr[i] = new Vertex(s,d);
    	}
    	msort(arr,0,m-1);
    	for(int i = 0;i < m-1;i++){
    		String s = arr[i].marvelch;
    		Vertex[] v = h.get(s);
            	int n1 = v[0].chId;
            	for(int j = 0;j<n1-1;j++){
            		System.out.print(v[j].marvelch);
            		System.out.print(",");
            	}
            	if(n1 > 0){
               		System.out.println(v[n1-1].marvelch);
            	}
        }
        if(m > 0){
        String s = arr[m-1].marvelch;
        Vertex[] v = h.get(s);
            int n1 = v[0].chId;
            for(int j = 0;j<n1-1;j++){
              	System.out.print(v[j].marvelch);
              	System.out.print(",");
            }
            if(n1 > 0){
               	System.out.println(v[n1-1].marvelch);
            }
        }
    }
    public static void main(String[] args) throws IOException{
	try{
           ArrayList<String> a = new ArrayList<String>();
           File nodes = new File(args[0]);
           BufferedReader b = new BufferedReader(new FileReader(nodes));
           String l = "";
           int n = 0;
           while ((l = b.readLine()) != null) {
              String[] arr = l.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
              if(n != 0){
              	 int r = arr[1].length();
                 boolean com = false;
                 for(int i = 0;i < r;i++){
                     if(arr[1].charAt(i) == ','){
                          com = true;
                          break;
                     }
                 }
                 String h = arr[1];
                 if(com == true){
                    h = arr[1].substring(1,arr[1].length()-1);
                 }
                 a.add(h);
              }
              n++;
           }
           int m = a.size();
           Vertex[] v = new Vertex[m];
           for(int i = 0;i<m;i++){
           	  v[i] = new Vertex(a.get(i),i);
           }
           Graph g = new Graph(m,v);
           File edge = new File(args[1]);
           BufferedReader br = new BufferedReader(new FileReader(edge));
           l = "";
           n = 0;
           while ((l = br.readLine()) != null) {
              String[] arr = l.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
              if(n != 0){
                 int len = arr.length;
              	 int w = Integer.parseInt(arr[len-1]);
              	 String s1 = arr[0];
              	 String s2 = arr[1];
                 int r = arr[0].length();
                 boolean com = false;
                 for(int i = 0;i < r;i++){
                     if(arr[0].charAt(i) == ','){
                          com = true;
                          break;
                     }
                 }
                 if(com == true){
                    s1 = arr[0].substring(1,arr[0].length()-1);
                 }
                 r = arr[1].length();
                 com = false;
                 for(int i = 0;i < r;i++){
                     if(arr[1].charAt(i) == ','){
                          com = true;
                          break;
                     }
                 }
                 if(com == true){
                    s2 = arr[1].substring(1,arr[1].length()-1);
                 }
                 if(g.hp.containsKey(s1) == true && g.hp.containsKey(s2) == true){
                   int k1 = g.hp.get(s1);
                   int k2 = g.hp.get(s2);
                   g.addEdge(g.varr[k1],g.varr[k2],w);
                 }
              }
              n++;
           }
           if(args[2].equals("average") == true){
           	  average(g);
           }
           else if(args[2].equals("rank")){
           	  Vertex[] v1 = new Vertex[g.vertexsize];
           	  rank(g,v1);
           }
           else if(args[2].equals("independent_storylines_dfs")){
           	  sc(g);
           }
        }
        catch(IOException ex) {
           ex.printStackTrace();
        }
    }
}
