#Récupére le fichier CSV
def lire_contenu_fichier(path):
    try:
        with open(path, 'r', encoding='utf-8') as fichier:
            contenu = fichier.read()
        return contenu
    except FileNotFoundError:
        print("Le fichier n'a pas été trouvé.")
        return None
    except Exception as e:
        print("Une erreur s'est produite lors de la lecture du fichier :", str(e))
        return None


# Filtre des colonnes:
def filtre_colonnes():
    colonnes = []
    colonnes.append(True) #order_id
    colonnes.append(True) #order_type
    colonnes.append(True) #order_date
    colonnes.append(True) #delivery_date
    colonnes.append(True) #order_item_num
    colonnes.append(True) #quantity
    colonnes.append(True) #total_retail_price
    colonnes.append(True) #cost_price_per_unit
    colonnes.append(True) #product_id
    colonnes.append(True) #product_name
    colonnes.append(True) #product_level
    colonnes.append(True) #product_level_name
    colonnes.append(True) #product_ref_id
    colonnes.append(True) #customer_id
    colonnes.append(True) #gender
    colonnes.append(False) #personal_id
    colonnes.append(False) #customer_name
    colonnes.append(False) #customer_firstname
    colonnes.append(False) #customer_lastname
    colonnes.append(True) #birth_date
    colonnes.append(False) #customer_address
    colonnes.append(False) #street_id
    colonnes.append(False) #street_number
    colonnes.append(True) #customer_type_id
    colonnes.append(True) #customer_type
    colonnes.append(True) #customer_group_id
    colonnes.append(True) #customer_group
    colonnes.append(True) #employee_id
    colonnes.append(False) #employee_start_date
    colonnes.append(False) #employee_end_date
    colonnes.append(True) #employee_job_title
    colonnes.append(True) #employee_salary
    colonnes.append(True) #employee_gender
    colonnes.append(False) #employee_birth_date
    colonnes.append(True) #employee_hire_date
    colonnes.append(False) #employee_term_date
    colonnes.append(False) #employee_name
    colonnes.append(True) #employee_country
    colonnes.append(True) #employee_org_level
    colonnes.append(True) #org_level_name
    colonnes.append(True) #country
    colonnes.append(True) #country_name
    colonnes.append(False) #population
    colonnes.append(True) #country_id
    colonnes.append(False) #continent_id
    colonnes.append(True) #country_formername
    colonnes.append(False) #continent_name
    colonnes.append(False) #supplier_id
    colonnes.append(False) #supplier_name
    colonnes.append(False) #supplier_address
    colonnes.append(False) #supplier_country

    indices_true = []
    for i in range(len(colonnes)):
        if colonnes[i]:
            indices_true.append(i)
    return indices_true

def afficheData(data,index_colonnes):
    newTable = []
    
    for ligne in data.split('\n'):            
        ligneS = ligne.split(';')
        #Parcours des index
        new_ligne = []
        for index in index_colonnes:
            try:
                new_ligne.append(ligneS[index])
            except:
                new_ligne.append('BUGG')
        newTable.append(new_ligne)
    
    return newTable

def imprimer_dans_txt(liste_str, nom_fichier):
    with open(nom_fichier, 'w') as fichier:
        for element in liste_str:
            fichier.write(';'.join(element) + '\n')  

def null():#
    # Exemple d'utilisation :
    chemin_fichier = "BDD_CASIOP_FULL.csv"
    contenu = lire_contenu_fichier(chemin_fichier)

    for ligneBrut in contenu.split('\n'):
        
        ligne = ligneBrut.split(';')

        Product_ID =  ligne[8]
        Product_Ref_ID = ligne[12]
        AssertProduc = Product_ID[:8] == Product_Ref_ID[:8]

        Emp_Hire_Date = ligne[34]
        EmployeeStart_Date = ligne [28]
        AssertHire = Emp_Hire_Date == EmployeeStart_Date
        
        Emp_Term_Date = ligne[35]
        Employee_End_Date = ligne [29]
        AssertEmploye = Emp_Term_Date == Employee_End_Date

        Customer_Type_ID = ligne[23]
        Customer_Group_ID = ligne[25]
        AssertCustomerID = Customer_Group_ID == Customer_Type_ID[:2]

        Customer_Type = ligne[24]
        Customer_Group = ligne[26]
        len_Customer_Group = len(Customer_Group)
        AssertCustomerType = Customer_Group == Customer_Type[:len_Customer_Group]

        print(Product_ID,Product_Ref_ID,AssertProduc,Emp_Hire_Date,EmployeeStart_Date,AssertHire,Emp_Term_Date,Employee_End_Date,AssertEmploye,Customer_Type_ID,Customer_Group_ID,AssertCustomerID,Customer_Type,Customer_Group,AssertCustomerType)


#MAIN
chemin_fichier = "files/BDD_CASIOP_FULL.csv"
data = lire_contenu_fichier(chemin_fichier) #Importation des données
index_colonnes = filtre_colonnes() #Filtre des collones
new_Table = afficheData(data,index_colonnes)
imprimer_dans_txt(new_Table,'files/BDD_CASIOP.csv')