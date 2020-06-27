package models;

public class Memoria {
    private Label[] memoria;

    public Memoria(){
        memoria = new Label[1024];
    }

    public Label[] getMemoria(){ return memoria; }

    public void setMemoria(Label[] memoria){ this.memoria = memoria; }

    public String toString(){
        String str ="";
        String s = "";

        for (int i = 0; i < 384; i++) {

            if (i < 10){ s =  "00"; } 
            else if (i < 100) { s = "0"; } 
            else { s = ""; }

            str+="["+ s + i + "]" + " " +"\n";
            if (memoria[i] != null) {
                str+="["+ s + i + "]" + " " + memoria[i].print()+"\n";
            }
        }
        
        return str;
    }

}