package models;

public class Memoria {
    private Label[] memoria;

    public Memoria(){
        memoria = new Label[1024];
    }

    public Label[] getMemoria(){ return memoria; }

    public void setMemoria(Label[] memoria){ this.memoria = memoria; }

    public String toString(){
        String s ="";
        for(int i=0; i < 1024; i++ ){
            s = memoria[i].print();
        }
        return s;
    }

}