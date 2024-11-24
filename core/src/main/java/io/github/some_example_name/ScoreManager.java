package io.github.some_example_name;

public class ScoreManager {
    private static ScoreManager instance; // Instancia única de la clase
    private int puntos; // Almacena los puntos

    // Constructor privado para evitar que la clase sea instanciada externamente
    private ScoreManager() {
        puntos = 0;
    }

    // Método estático para obtener la única instancia de la clase
    public static ScoreManager getInstance() {
        if (instance == null) { // Si no existe la instancia, la crea
            instance = new ScoreManager();
        }
        return instance; // Retorna siempre la misma instancia
    }

    // Métodos adicionales para manejar el puntaje
    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void resetPuntos() {
        puntos = 0;
    }
}
