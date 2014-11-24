package com.pdm.servicebatalhanaval;

import java.util.Random;

public class GameManager {

	private char[][] mar = new char[9][9];

	public GameManager() {
		this.iniciateMatrix();
		this.criaNavios();
		this.criarSubmarinos();
		this.criarSubmarinos();
		this.criarTanques();
		this.criarTanques();
		imprimeMatriz();
	}

	private void iniciateMatrix() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.mar[i][j] = '_';
			}
		}
	}

	private int getRandomNumber() {
		Random random = new Random();
		return random.nextInt((9 - 1) + 1);
	}

	private int getEixo() {
		Random random = new Random();
		return random.nextInt((2 - 1) + 1) + 1;
	}

	private void criaNavios() {
		if (getEixo() == 1) {
			// Vertical
			int inicioV = getRandomNumber();
			int inicioH = getRandomNumber();
			while (inicioV > 6 || inicioH > 6
					|| this.mar[inicioV][inicioH] != '_'
					|| this.mar[inicioV][inicioH + 1] != '_'
					|| this.mar[inicioV][inicioH + 2] != '_') {
				inicioV = getRandomNumber();
				inicioH = getRandomNumber();
			}

			this.mar[inicioV][inicioH] = 'N';
			this.mar[inicioV][++inicioH] = 'N';
			this.mar[inicioV][++inicioH] = 'N';
		} else {
			// // Horizontal
			int inicioV = getRandomNumber();
			int inicioH = getRandomNumber();
			while (inicioV > 6 || inicioH > 6
					|| this.mar[inicioV][inicioH] != '_'
					|| this.mar[inicioV + 1][inicioH] != '_'
					|| this.mar[inicioV + 2][inicioH] != '_') {
				inicioV = getRandomNumber();
				inicioH = getRandomNumber();
			}

			this.mar[inicioV][inicioH] = 'N';
			this.mar[++inicioV][inicioH] = 'N';
			this.mar[++inicioV][inicioH] = 'N';
		}
	}

	private void criarSubmarinos() {
		int inicioV = getRandomNumber();
		int inicioH = getRandomNumber();
		while (inicioV > 7 || inicioH > 7 || this.mar[inicioV][inicioH] != '_'
				|| this.mar[inicioV + 1][inicioH] != '_') {
			inicioV = getRandomNumber();
			inicioH = getRandomNumber();
		}

		this.mar[inicioV][inicioH] = 'S';
		this.mar[++inicioV][inicioH] = 'S';
	}

	private void criarTanques() {
		int inicioV = getRandomNumber();
		int inicioH = getRandomNumber();
		while (this.mar[inicioV][inicioH] != '_') {
			inicioV = getRandomNumber();
			inicioH = getRandomNumber();
		}

		this.mar[inicioV][inicioH] = 'T';
	}

	public void imprimeMatriz() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(this.mar[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

	public String atirar(int x, int y) {
		x--;
		y--;
		String result = null;
		if (mar[y][x] != '@' && mar[y][x] != '*') {
			if (mar[y][x] == 'N') {
				result = "10";
				mar[y][x] = '*';
			} else if (mar[y][x] == 'S') {
				result = "5";
				mar[y][x] = '*';
			} else if (mar[y][x] == 'T') {
				result = "4";
				mar[y][x] = '*';
			} else {
				result = "0";
				mar[y][x] = '@';
			}
			
		}else{
			result = "Local já acertado";
		}

		if(checkEnd()){
			result+="@fim";
		}
		
		return result;
	}
	
	public boolean checkEnd(){
		boolean c = false; 
		int count = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if((this.mar[i][j] == '@') || (this.mar[i][j] == '*')){
					count++;
				}
			}
		}
		
		if(count == 81 || checkNoObjects()){
			c = true;
		}
		
		System.out.println("TESTESTESTESTESTESTE" + String.valueOf(count));
		return c;
	}
	
	public boolean checkNoObjects(){
		boolean c = false; 
		int count = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(this.mar[i][j] == '*'){
					count++;
				}
			}
		}
		
		if (count==9){
			c = true;
		}
		return c;
	}

	public char[][] getMar() {
		return this.mar;
	}

}
