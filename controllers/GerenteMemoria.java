package controllers;
public class GerenteMemoria {
    private int[] b = new int[8];
    private CPU cpu;
    private boolean[] livres;

    public GerenteMemoria() {
        for (int i = 0; i < 8; i++) {
            b[i] = -1;
        }
        setLivres(new boolean[8]);
    }

    public boolean[] getLivres() {return livres;}
    public void setLivres(boolean[] livres) {this.livres = livres;}


    public boolean desalocaParticao(){
        return true;
    }
    public int primeiroLivre(){
        int i=0;
        for(boolean livre : livres){
            if(livre==false){
                livres[i]=true;
                return i;
            }
            i++;
        }
        
        System.out.println("GM - livre: "+ livres[0]);
        return -1;
    }
    public int alocar(int ID) {
        int aux = -1;

        for (int i = 0; i < 8; i++) {
            if (b[i] == ID) {
                aux = i + 1;
            }
        }
        if (aux != -1) {
            return aux * 128;
        } else {
            for (int i = 0; i < 8; i++) {
                if (b[i] == -1) {
                    b[i] = ID;
                    return (i + 1) * 128;
                }
            }
        }
        return aux;
    }

    public void Desaloca(int limiteSup) {
        for (int i = limiteSup - 127; i < limiteSup; i++) {
            cpu.desalocaMemoria(i);
        }
    }
}