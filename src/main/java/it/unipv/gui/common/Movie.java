package it.unipv.gui.common;

/**
 * Oggetto che rappresenta un film.
 * Il parametro "Codice" viene usato come identificativo
 *    (esempio: se devo modificare le informazioni di un film ci accedo e lo sovrascrivo
 *    tramite il codice).
 */
public class Movie implements Comparable<Movie> {
    private String codice;
    private String locandinaPath;
    private String titolo;
    private String genere;
    private String regia;
    private String cast;
    private String durata;
    private String anno;
    private String trama;
    private MovieTYPE tipo;
    private MovieStatusTYPE status;

    public String getCodice() { return codice; }

    public void setCodice(String codice) { this.codice = codice; }

    public String getLocandinaPath() { return locandinaPath; }

    public void setLocandinaPath(String locan) { this.locandinaPath = locan; }

    public String getTitolo() { return titolo; }

    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getGenere() { return genere; }

    public void setGenere(String genere) { this.genere = genere; }

    public String getRegia() { return regia; }

    public void setRegia(String regia) { this.regia = regia; }

    public String getCast() { return cast; }

    public void setCast(String cast) { this.cast = cast; }

    public String getDurata() { return durata; }

    public void setDurata(String durata) { this.durata = durata; }

    public String getAnno() { return anno; }

    public void setAnno(String anno) { this.anno = anno; }

    public String getTrama() { return trama; }

    public void setTrama(String trama) { this.trama = trama; }

    public MovieTYPE getTipo() { return tipo; }

    public void setTipo(MovieTYPE tipo) { this.tipo = tipo; }

    public MovieStatusTYPE getStatus() { return status; }

    public void setStatus(MovieStatusTYPE status) { this.status = status; }

    @Override
    public int compareTo(Movie o) {
        return this.getTitolo().compareToIgnoreCase(o.getTitolo());
    }
}
