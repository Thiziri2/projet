package circuit;

import java.awt.Color;
import java.awt.image.BufferedImage;

import enums.Terrain;
import exceptions.TerrainException;


import java.awt.Graphics;

import java.io.*;
import javax.imageio.ImageIO;


public class TerrainTools {
	public TerrainTools() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Terrain terrainFromChar(char c) throws TerrainException{
		switch (c){
		case '.':  return Terrain.Route;
		case 'g':  return Terrain.Herbe;
		case 'b':  return Terrain.Eau; 
		case 'o':  return Terrain.Obstacle; 
		case 'r':  return Terrain.BandeRouge;
		case 'w':  return Terrain.BandeBlanche;
		case '*':  return Terrain.StartPoint;
		case '!':  return Terrain.EndLine;
		case 'm':  return Terrain.Boue;
		default : throw new TerrainException("valeur non trouve");
		}
	}
	
	public static char charFromTerrain(Terrain t){
		int indexT= 0;
		Terrain tab[]=Terrain.values();
		for(int i=0;i<tab.length;i++){
			if(t.equals(tab[i]))  indexT=i;
		}		
		return Terrain.conversion[indexT];
	}
	
	public static Color terrainToRGB(Terrain t){
		int indexT= 0;
		Terrain tab[]=Terrain.values();
		for(int i=0;i<tab.length;i++){
			if(t.equals(tab[i]))  indexT=i;
		}		
		return Terrain.convColor[indexT];
	}
	
	public static boolean isRunnable(Terrain t){
	if(t.equals(Terrain.Route)||t.equals(Terrain.Boue)||t.equals(Terrain.Route)||t.equals(Terrain.BandeBlanche)||t.equals(Terrain.BandeRouge)||t.equals(Terrain.EndLine)||t.equals(Terrain.StartPoint))
		return true;
	else return false;
	}
	public static Terrain[][] lectureFichier(String fichier){
		BufferedReader in=null;
		try { 
			FileInputStream f=new FileInputStream(fichier);
		   in = new BufferedReader(new InputStreamReader(f));
		   
		   String ligne = in.readLine();
		   
		   int nbc=Integer.parseInt(ligne);
		   ligne = in.readLine();
		   int nbl=Integer.parseInt(ligne);
		   Terrain t[][]=new Terrain[nbl][nbc];
		    
		   char tab[]=new char[nbc];
			   for(int i=0;i<nbl;i++){
				   ligne = in.readLine();
				   tab = ligne.toCharArray();
				   for(int j=0;j<nbc;j++){
					  t[i][j]=TerrainTools.terrainFromChar(tab[j]);
				   }
			  }
	
			   
		   in.close();
		   return t; 
		} catch (Exception e) {
			   e.printStackTrace();
			   System.err.println("Invalid Format ");
			 }
		return null; 
	}
	

	public static BufferedImage imageFromCircuit(Terrain[][] track) {
		BufferedImage img = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
 		Graphics g = img.getGraphics();
 		for(int i=0;i<1024;i++) {
 			for(int j=0;j<768;j++) {
 				g.setColor(terrainToRGB(track[j][i]));
 				g.drawLine(i, j, i, j);
 			}
 		}
 	    try {
 	        File outputfile = new File("saved.png");
 	        ImageIO.write(img, "png", outputfile);
 	     } catch (IOException e) {
 	        System.out.println("Erreur lors de la sauvegarde");
 	     }
 	    return img;
    	}
	
}	
