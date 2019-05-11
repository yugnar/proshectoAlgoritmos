using System;
using System.Collections.Generic;
using System.Linq;



namespace Proyecto_Algoritmos {
    public static class Problem2 {
        static void Main(string[] args) {

            var userInput = Console.ReadLine();
            var nChop = Convert.ToInt32(userInput);
            var counter = 0;
            
            List<Chopstick> palitos = new List<Chopstick>();
            var delimiters = new char[] { ' ', ',', '(', ')' };
            
            
            for (var i = 0; i < nChop ; i++) {
                var sInputs = Console.ReadLine();

               var sPoints = sInputs.Split(delimiters, StringSplitOptions.RemoveEmptyEntries);

               var x1 = Convert.ToInt32(sPoints[0]);
               var y1 = Convert.ToInt32(sPoints[1]);
               var x2 = Convert.ToInt32(sPoints[2]);
               var y2 = Convert.ToInt32(sPoints[3]);
               
               palitos.Add( new Chopstick( new Points(x1, y1), new Points(x2, y2) ) );

            }
            

            var resultsPerm = Permute<Chopstick>(palitos).ToArray();

            for (int i = 0; i < resultsPerm.Length ; i++) {
                if ( DoIntersec(resultsPerm[i].First().P1,
                    resultsPerm[i].First().P2, 
                    resultsPerm[i].Last().P1,
                    resultsPerm[i].Last().P2) ) {
                    counter++;
                }
            }

            if (counter > nChop) {
                Console.WriteLine(0);
            } else if (counter == 0) {
                Console.WriteLine(nChop);
            }
        }
        
        // Empieza punto de interseccion

        static bool OnSemgent(Points p, Points q, Points r) {
            if (q.x <= Math.Max(p.x, r.x) && q.x >= Math.Min(p.x, r.x) &&
                q.y <= Math.Max(p.y, r.y) && q.y >= Math.Min(p.y, r.y))
                return true;

            return false;
        }

        static int Orientation(Points p, Points q, Points r) {
            
            var val = (q.y - p.y) * (r.x - q.x) -
                      (q.x - p.x) * (r.y - q.y);

            if (val == 0) return 0; 

            return (val > 0) ? 1 : 2; 
        }

        static bool DoIntersec(Points p1, Points q1, Points p2, Points q2) {
            
            int o1 = Orientation(p1, q1, p2);
            int o2 = Orientation(p1, q1, q2);
            int o3 = Orientation(p2, q2, p1);
            int o4 = Orientation(p2, q2, q1);
            
            if (o1 != o2 && o3 != o4)
                return true;

            
            if ( o1 == 0 && OnSemgent(p1, p2, q1) ) return true;

            
            if (o2 == 0 && OnSemgent(p1, q2, q1)) return true;

            
            if (o3 == 0 && OnSemgent(p2, p1, q2)) return true;

            
            if (o4 == 0 && OnSemgent(p2, q1, q2)) return true;

            return false;  
        }
        
        //Termina Punto de interseccion
        
        //Empieza permutaciones
        
        public static IEnumerable<IEnumerable<T>> Permute<T>(this IEnumerable<T> sequence)
        {
            if (sequence == null)
            {
                yield break;
            }

            var list = sequence.ToList();

            if (!list.Any())
            {
                yield return Enumerable.Empty<T>();
            }
            else
            {
                var startingElementIndex = 0;

                foreach (var startingElement in list)
                {
                    var remainingItems = list.Where((e,i) => i != startingElementIndex);

                    foreach (var permutationOfRemainder in remainingItems.Permute())
                    {
                        yield return startingElement.Concat(permutationOfRemainder);
                    }

                    startingElementIndex++;
                }
            }
        }
        
        private static IEnumerable<T> Concat<T>(this T firstElement, IEnumerable<T> secondSequence)
        {
            yield return firstElement;
            if (secondSequence == null)
            {
                yield break;
            }

            foreach (var item in secondSequence)
            {
                yield return item;
            }
        }
        
        //termina permutaciones
    }

    class Chopstick {
        private Points p1;
        private Points p2;

        public Chopstick(Points p1, Points p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public Points P1 {
            get { return p1; }
            set { p1 = value; }
        }

        public Points P2 {
            get { return p2; }
            set { p2 = value; }
        }
    }
    
    class Points {
        public int x;
        public int y;

        public Points(int x, int y) {
            this.x = x;
            this.y = y;
                
        }
    }
    
}