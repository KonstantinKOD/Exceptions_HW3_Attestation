package HW3.AttestationWork;

/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида

<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

Не забудьте закрыть соединение с файлом.

При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HW_3 {
    static String fio = "";
    static String birthDay;
    static long phoneNumber;
    static char male;

    public static void main(String[] args) {
        try {
            inputData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inputData() throws IOException {
        System.out.println ("Введите ФИО (через пробел), дату рождения (dd.mm.yyyy), номер телефона, пол (f/m).");
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        String [] arr = userInput.split(" ");
        sc.close();
        if (arr.length != 6){
            try {
                throw new Exception("Не все данные заполнены.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length() == 1){
                checkMale(arr[i].charAt(0));
                if (arr[i].equals("f") | arr[i].equals("m")){
                    male = arr[i].charAt(0);
                }
            }
            if (arr[i].matches("^[0-9]*$")){
                phoneNumber = Long.valueOf(arr[i]);
            }
            if (arr[i].matches("^[0-9.]*$") && arr[i].length() < 11){
                checkBirhday(arr[i]);
                birthDay = arr[i];
            }
            if (arr[i].matches("^[a-zA-Zа-яА-Я]*$") & arr[i].length() > 1){
                fio += arr[i] + " ";
            }
            if (arr[i].matches (".*[a-zA-Zа-яА-Я].*") && arr[i].matches(".*[0-9].*")){
                try {
                    throw new Exception("Неверный ввод");
                } catch (Exception e) {
                    System.out.println(e.getMessage() + arr [i]);
                }
            }
        }

        String[] familyName = fio.split(" ");
        File file = new File(String.format("%s.txt", familyName[0]));
        boolean writeOverwrite = true;
        try {
            if (file.createNewFile()){
                System.out.println("Файл успешно создан!");
                writeOverwrite = false;
            } else{
                System.out.println("Файл уже существует.");
            }
            FileWriter writer = new FileWriter (file, writeOverwrite);
            writer.write("<" + fio + ">" + "<" + birthDay + ">" + "<" + phoneNumber + ">" + "<" + male + ">\n");
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }

    public static void checkMale(char male) {
        if (male != 102 && male != 109){
            System.err.println(male);
            try {
                throw new Exception("Неверный параметр пола, введите f или m. Введено " + male);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void checkBirhday(String birthDay) {
        if (!birthDay.matches("\\d{2}.\\d{2}.\\d{4}")){
            try {
                throw new Exception("Неверный формат даты рождения");
            } catch (Exception e) {
                System.out.println(e.getMessage() + birthDay);
            }
        }
    }
}

