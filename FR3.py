import math

class Ciudad () :

	def __init__ ( self , nombre, premio):
		self.nombre = nombre
		self.premio = premio
		
	def getNombre (self):
		return self.nombre
		
	def getPremio (self):
		return self.premio


NC = int (input())

coordenadas=[]
ciudades = []


for i in range(NC):
	nom , xy, vt = input().split()
	coordenadas.append (xy.strip('()'))
	c = Ciudad(nom,int(vt))
	ciudades.append(c)
	

#print (ciudades)
#print (coordenadas)

Grafo = [[0 for x in range(NC)] for y in range(NC)]

for i in range (NC):
	xs,ys = coordenadas[i].split(',')
	xsi = int(xs)
	ysi = int (ys)
	for j in range (NC):
		if i == j :
			Grafo[i][j] = 0
			continue
		if Grafo[i][j] != 0:
			continue
		xd,yd = coordenadas[j].split(',')
		xdi = int(xd)
		ydi = int (yd)
		distancia = math.sqrt((xdi-xsi)**2+(ydi-ysi)**2)
		Grafo [i][j] = distancia
		if i == 0:
		   Grafo [j][i] = float("Inf")
		else:
		   Grafo [j][i] = distancia
	
#print (Grafo)

ip = 0

path_ciudades=[]
max_path_ciudades = []
path_ciudades.append(int(0))

distancia_recorrida = 0
premio_total= 0
maximo_premio = 0 
ciudad = 0 


jp = ip + 1

while distancia_recorrida <=100 and jp <= len(ciudades):
	i= ip 	
	j = jp
	path_ciudades.clear()
	path_ciudades.append(ip)
	while j < len (ciudades):
		#print (i,j)
		if Grafo[i][j] != 0 and Grafo[i][j] != float("Inf"):
			distancia_recorrida = distancia_recorrida + Grafo[i][j]
			if distancia_recorrida <= 100:
				path_ciudades.append(j)
				premio_total = premio_total + ciudades[j].getPremio()
				if (premio_total >= maximo_premio):
					maximo_premio = premio_total
					maxima_distancia = distancia_recorrida
					max_path_ciudades = path_ciudades.copy()
				#print (path_ciudades, premio_total, distancia_recorrida)
				i = j
				j = j +1
			else :
				j = j + 1
				continue
		else:
			j = j + 1
	jp = jp + 1 
	distancia_recorrida = 0
	premio_total = 0


		
#print (max_path_ciudades, maximo_premio,maxima_distancia)

for i in range(len(max_path_ciudades)):
	print (ciudades[max_path_ciudades[i]].getNombre())
		
	
	

