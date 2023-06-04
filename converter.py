

def main():
    path = "M0.147411 10.6996V7.72331L7.86897 7.70225V1.02661H25.2495V6.88798H28.296V10.4118H25.6075V9.48523H22.2732V3.91167H11.3998V10.6996H0.147411Z"
    offsetx = 0.15
    offsety = 7.2

    scalar = 1.07

    height = 18
    
    
    path = path.replace("M", " M");
    path = path.replace("V", " V");
    path = path.replace("L", " L");
    path = path.replace("H", " H");
    path = path.replace("Z", " Z");
    path = path.replace("  ", " ");

    list = path.split(" ");

    x = 0
    y = 0

    #for i in range(0, len(list)):
    i = -1
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
