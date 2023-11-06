using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Client
{
    /// <summary>
    /// Logica di interazione per Partita.xaml
    /// </summary>
    public partial class Partita : Window
    {
        public Partita()
        {
            InitializeComponent();

            // Imposta la larghezza e l'altezza della finestra
            this.Width = 1200;  // Imposta la larghezza della finestra a 1200 pixel
            this.Height = 720;  // Imposta l'altezza della finestra a 700 pixel

            // Crea una nuova griglia
            Grid campoGioco = new Grid();

            // Imposta le proprietà della griglia
            campoGioco.HorizontalAlignment = HorizontalAlignment.Center;
            campoGioco.VerticalAlignment = VerticalAlignment.Center;
            campoGioco.ShowGridLines = true;  // Mostra le linee della griglia

            // Aggiungi le righe alla griglia
            for (int i = 0; i < 6; i++)
            {
                var rowDefinition = new RowDefinition();
                rowDefinition.Height = new GridLength(60);  // Imposta l'altezza della riga
                campoGioco.RowDefinitions.Add(rowDefinition);
            }

            // Aggiungi le colonne alla griglia
            for (int i = 0; i < 7; i++)
            {
                var columnDefinition = new ColumnDefinition();
                columnDefinition.Width = new GridLength(60);  // Imposta la larghezza della colonna
                campoGioco.ColumnDefinitions.Add(columnDefinition);
            }

            // Aggiungi le celle alla griglia
            for (int row = 0; row < 6; row++)
            {
                for (int col = 0; col < 7; col++)
                {
                    Button cell = new Button();
                    cell.Background = Brushes.White;  // Imposta il colore di sfondo della cella
                    cell.Width = 60;  // Imposta la larghezza del pulsante
                    cell.Height = 60;  // Imposta l'altezza del pulsante
                    Grid.SetRow(cell, row);  // Imposta la riga della cella
                    Grid.SetColumn(cell, col);  // Imposta la colonna della cella
                    campoGioco.Children.Add(cell);  // Aggiungi la cella alla griglia
                }
            }

            // Aggiungi la griglia al layout principale dell'applicazione
            this.Content = campoGioco;
        }
    }
}
