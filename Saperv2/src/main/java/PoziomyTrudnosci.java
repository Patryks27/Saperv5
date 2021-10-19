public enum PoziomyTrudnosci {
    Łatwy(10,8),
    Średni(22,12),
    Trudny(40,16);

    private int  bombs;
    private int elements;

    PoziomyTrudnosci(int bombs, int elements){
        this.bombs = bombs;
        this.elements = elements;
    }
    public int getBombs() { return this.bombs;}
    public int getElements(){return  this.elements;}

}

