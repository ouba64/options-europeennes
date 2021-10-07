## Description and usage
This tool allows you to calculate the call and put of a European option using two methods:
- Black-Scholes
- Simulation de Monte-Carlo

It puts side by side the results obtained by the two methods so that they can be compared.  
To run the program, launch the `pricer.vue.Main` class, this is the only class having a main method.  

The UI is made up of 3 main parts. 

![Drag Racing](pricer.fw.png)    

[1] Paramètres: Those are the parameters used to calculate the call and the put for the 2 methods. Every parameter that is preceded with (MC) is only used for the computation of the features for Monte-Carlo.  
[2] Résultats: Those are the results (call and put) of the last computation for the 2 methods.    
[3] Lancer: This button is used to launch the computation of the call and put for both methods by using the parameters in [1]  
[4] Effacer: Clears the plots in [5]  
[5] Résultats: This is the plotting area. Typically, the call value for both methods is drawn in the upper area and the put in the lower area. The features (call or put) are drawn vs number of simulations.   
 
## Implementation
Let 

S = <Prix sous-jacent> 
T = <Maturity>/365  
K = <Prix strike>  
r = <Taux sans risque>  
$\sigma$ = <Taux volatilité>  

### Black-Scholes
The formula to compute the put P and the call C of the European option according to Black-Scholes method is :  
 
![](C_and_P.PNG)   
where :  

![](d1_and_d2.PNG) 


$ N $ = Cumulative distribution function of the standard normal distribution N(0,1)

### Monte-Carlo simulation

