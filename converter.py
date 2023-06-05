

def main():
    #level_1 = "M0.147411 10.6996V7.72331L7.86897 7.70225V1.02661H25.2495V6.88798H28.296V10.4118H25.6075V9.48523H22.2732V3.91167H11.3998V10.6996H0.147411Z"
    #level_2 = "M1.47552 11.3693V14.375H9.37101V11.0553H15.7323V7.60996H22.3986V4.48765H33.7573V1.15898H17.3024V4.47868H12.2062V7.80734H6.36534V11.3513H1.47552"
    path = "M3.13655 8.39667V15.6583L6.31581 15.6644V11.0816H20.7354V4.13121H5.13197V2.83754H21.7361V17.1473H29.0893V14.6637H24.8178V0.341725H2.23342V6.81009H17.5561V8.39667H3.13655Z"
    offsetx = 0.05
    offsety = 0.05

    scalar = 1.0 #1.07

    height = 18
    
    
    path = path.replace("M", " M");
    path = path.replace("V", " V");
    path = path.replace("L", " L");
    path = path.replace("H", " H");
    path = path.replace("Z", " Z");
    path = path.replace("  ", " ");

    list = path.split(" ");

    print(list)

    x = 0
    y = 0

    #for i in range(0, len(list)):
    i = 0
    while i < len(list):
        if len(list[i]) == 0:
            i += 1
            continue

        if list[i][0] == "M":
            x = float(list[i][1:])
            i += 1
            y = float(list[i])
        elif list[i][0] == "V":
            y = float(list[i][1:])
        elif list[i][0] == "H":
            x = float(list[i][1:])
        elif list[i][0] == "L":
            x = float(list[i][1:])
            i += 1
            y = float(list[i])
        
        i += 1
        
        print("<p>" + str(round(x*scalar+offsetx, 1)) + ";" + str(-round(height-(height-(y+offsety))*scalar, 1)) + "</p>")



if __name__ == "__main__":
    main()
