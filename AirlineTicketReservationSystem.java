import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class AirlineTicketReservationSystem {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

        LocalDate dt = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String presentDate = dt.format(df);

        LocalDate dtNext = dt.plusMonths(3);
        String nextDate = dtNext.format(df);

        int op = 1, cnf, pmt, id = 0, flag = 0;

        String section = new String();
        String[] city = {"Delhi", "Mumbai", "Chennai", "Kolkata"};
        String[] airportType = new String[] {"Single", "Multihop"};
        int[] arr = new int[2];
        int[] mark = new int[2];

        System.out.println("\n---------------------------------------------------------------------");
        System.out.println("--------WELCOME TO SPICEJET AIRLINE TICKET RESERVATION CENTER--------");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("For Flight Booking of : Delhi-Mumbai-Chennai-Kolkata");
        System.out.println("Book your advance tickets between " + presentDate + " to " + nextDate + " now.\n");

        Aircraft[] acft = new Aircraft[5];
        initializeAircrafts(acft);
        
        int totalRoutes = 20;
        Route[] flt = new Route[totalRoutes];
        initializeFlightRoutes(flt, totalRoutes, city);

        Ticket[] tck = new Ticket[10];
        for (int i = 0; i < 10; i++) {
            tck[i] = new Ticket();
            tck[i].ticketId = 111;
        }

        do {
            System.out.println("1.Flight Booking\t\t2.Cancellation of Ticket\t\t3.To check details of Reserved Ticket");
            System.out.print("Enter your choice : ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    System.out.println("\n0.Delhi\t\t1.Mumbai\t\t2.Chennai\t\t3.Kolkata");
                    System.out.print("Select your Departure city : ");
                    int depr = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Select your Arrival city : ");
                    int arvl = sc.nextInt();
                    sc.nextLine();

                    if (depr >= 0 && depr < 4 && arvl >= 0 && arvl < 4 && depr != arvl) {        
                        String date = getTravelDate(sc, presentDate, nextDate, df);

                        System.out.printf("\n-----------------Available Flights :------------------\n\n");
                        for (int i = 0; i < 20; i++) {
                            if ((flt[i].src.compareTo(city[depr]) == 0) && (flt[i].dest.compareTo(city[arvl]) == 0)) {
                                System.out.println("Press  " + i);
                                flt[i].getroute();
                                acft[i % 5].getdetails();
                                System.out.println("\n\n");
                                mark[i % 2] = i;
                            }
                        }
                        System.out.println("Please be careful while Entering Flight choice : ");
                        int tkt = sc.nextInt();
                        sc.nextLine();

                        if (tkt != mark[0] && tkt != mark[1]) {
                            System.out.println("Enter valid choice!!");
                            break;
                        } else {
                            System.out.println("\nSelected Flight :");
                            flt[tkt].getroute();
                            arr = seatAvailability(sc);
                            if (arr[2] == 1) {
                                if (arr[1] == 0) {
                                    section = "Economy Class";
                                } else if (arr[1] == 1) {
                                    section = "First Class";
                                }
                                System.out.println("\nPress 1 to confirm selected flight else press 0 :");
                                cnf = sc.nextInt();
                                sc.nextLine();

                                if (cnf == 1) {
                                    System.out.println("press 1 to make payment and book ticket else press 0 : ");
                                    pmt = sc.nextInt();
                                    sc.nextLine();

                                    if (pmt == 1) {
                                        System.out.println("\nTicket booked sucessfully!!\n");
                                        System.out.println("\n----------------Your Ticket Details--------------");
                                        tck[id].setticket(id, date, section, arr[0], tkt);
                                        if (arr[1] == 1)
                                            flt[id].fare *= 2;
                                        tck[id].getticket();
                                        flt[tkt].getroute();
                                        id++;
                                    }
                                }
                            }
                        }
                    }
                    else if (depr == arvl)
                        System.out.println("\n\nError! You have entered same Departure and Arrival city!!");
                    else
                        System.out.println("\nError! Please enter a Valid City Choice!!");
                    break;

                case 2:
                    System.out.print("Enter your Ticket Id : ");
                    int del = sc.nextInt();
                    sc.nextLine();
                    flag = 0;
                    for (int i = 0; i < 10; i++) {
                        if (del == tck[i].ticketId) {
                            System.out.println("\n----------------Your Ticket Details--------------");
                            tck[del].getticket();
                            flt[tck[del].code].getroute();
                            System.out.println("Press 1 to confirm Cancellation of Your Ticket else press 0 : ");
                            int d = sc.nextInt();
                            sc.nextLine();
                            flag = 1;
                            if (d == 1) {
                                tck[del].ticketId = 111;
                                System.out.println("\nYour Ticket has been cancelled Successfully.");
                                System.out.println("You will get Refund Amount within two days.\n\n");
                            }
                            break;
                        }
                    }
                    if (flag == 0)
                        System.out.println("No such ticket exist of your entered Ticket Id!");
                    break;

                case 3:
                    System.out.print("Enter your Ticket Id : ");
                    int find = sc.nextInt();
                    sc.nextLine();
                    int temp1 = generateRandomNumber(2);
                    int temp2 = generateRandomNumber(2);
                    flag = 0;
                    for (int i = 0; i < 10; i++) {
                        if (find == tck[i].ticketId) {
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.print("Your flight will take off from " + flt[tck[find].code].src);
                            System.out.println(" airport which is a " + airportType[temp1] + " runway airport.");
                            System.out.print("Your flight will land on " + flt[tck[find].code].dest);
                            System.out.println(" airport which is a " + airportType[temp2] + " runway airport.");
                            System.out.println("\n----------------Your Ticket Details--------------");
                            tck[find].getticket();
                            System.out.println();
                            flt[tck[find].code].getroute();
                            acft[tck[find].code % 5].getdetails();
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        System.out.println("No such ticket exist of your entered Ticket Id!");
                    }
                    break;

                default:
                    System.out.println("Error! Please enter a valid choice!");
            }

            System.out.println("\nDo you want to continue? press 1 if 'yes' and press 0 if 'no': ");
            op = sc.nextInt();
            sc.nextLine();
        } while (op != 0);

		System.out.println("\n\n--------------------------------------------------------------------");
		System.out.println("------------------------THANK YOU FOR VISITING----------------------");
		System.out.println("---------------SPICEJET AIRLINE TICKET RESERVATION CENTER-----------");
		System.out.println("--------------------------------------------------------------------");

        sc.close();
	}

    public static void initializeAircrafts(Aircraft[] acft) {
        for (int i = 0; i < 5; i++)
            acft[i] = new Aircraft();

        acft[0].setdetails("Airbus A320-200", "passenger", 180, 730);
        acft[1].setdetails("Boeing 737-700", "passenger", 132, 510);
        acft[2].setdetails("Airbus A330-900neo", "passenger", 210, 650);
        acft[3].setdetails("Boeing 737-900", "passenger", 140, 580);
        acft[4].setdetails("Airbus A340-300F", "passenger", 126, 850);
    }

    public static int generateRandomNumber(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber+1);
    }

    public static int generateRandomNumber(int minNumber, int maxNumber) {
        Random random = new Random();
        return minNumber + random.nextInt(maxNumber-minNumber+1);
    }

    public static int[] generateRandomArray(int totalRoutes, int minNumber, int maxNumber) {
        int[] routeCodes = new int[totalRoutes];
        Set<Integer> uniqueNumbers = new HashSet<>();

        while (uniqueNumbers.size() < totalRoutes) {
            int randomNumber = generateRandomNumber(minNumber, maxNumber);
            uniqueNumbers.add(randomNumber);
        }

        int index = 0;
        for (int number : uniqueNumbers) {
            routeCodes[index] = number;
            index++;
        }
        return routeCodes;
    }

    public static void initializeFlightRoutes(Route[] flt, int totalRoutes, String[] city){
        String[] trafficTypes = {"low", "busy", "moderate"};
        int[] routeCodes = generateRandomArray(totalRoutes, 999, 9999);
        int[] sourceAndDestIndexPair;
        int trafficType, srcIndex, destIndex, flightFare, flightFrequency;
        String formattedRandomTime;

        for (int i = 0; i < totalRoutes; i++) {
            trafficType = generateRandomNumber(trafficTypes.length-1);
            flightFare = generateRandomNumber(1200, 15000);
            flightFrequency =  generateRandomNumber(2, 8);
            sourceAndDestIndexPair = generateRandomArray(2, 0, city.length-1);
            srcIndex = sourceAndDestIndexPair[0];
            destIndex = sourceAndDestIndexPair[1];
            formattedRandomTime = String.format("%02d:%02d:00", generateRandomNumber(24), generateRandomNumber(60));

            flt[i] = new Route();
            flt[i].setroute(trafficTypes[trafficType], routeCodes[i], city[srcIndex], city[destIndex], formattedRandomTime, flightFare, flightFrequency);
        }
    }

    public static int getNumberOfTicketsToBook(Scanner sc, String className, int maxSeats) {
        int ticketsToBeBooked = 0;
        do {
            System.out.print("\nHow many Tickets do you want to book in " + className + " class: ");
            ticketsToBeBooked = sc.nextInt();
            sc.nextLine();

            if (ticketsToBeBooked <= 0 || ticketsToBeBooked > maxSeats) {
                System.out.println("Enter a valid number of tickets between 1 and " + maxSeats + " in " + className + " class!");
            }
        } while (ticketsToBeBooked <= 0 || ticketsToBeBooked > maxSeats);

        return ticketsToBeBooked;
    }

	public static int[] seatAvailability(Scanner sc) {
        int[] seat = new int[3];
        int bookedEconomyclass = 0;
        int bookedFirstclass = 0;

        int totalSeats = (int) (Math.random() * 31 + 30);
        int EconomySeats = (int) (totalSeats * 0.7);
        int FirstclassSeats = totalSeats - EconomySeats;

        int freeEconomySeats = generateRandomNumber(1, EconomySeats);
        int freeFirstclassSeats = generateRandomNumber(1, FirstclassSeats);
        int totalFreeSeats = freeEconomySeats + freeFirstclassSeats;

        System.out.println("\n\nTotal Available Seats are : " + totalFreeSeats);
        System.out.println("Available  Economy Seats are : " + freeEconomySeats);
        System.out.println("Available First Class Seats are : " + freeFirstclassSeats);

        System.out.println("\nTo book an Economy class Ticket Enter 0 or");
        System.out.println("To book a First class Ticket Enter 1 : ");
        int choice = sc.nextInt();
        sc.nextLine();

        String className = (choice == 0) ? "Economy" : "First class";

        int maxSeats = (choice == 0) ? freeEconomySeats : freeFirstclassSeats;
        int ticketsToBeBooked = getNumberOfTicketsToBook(sc, className, maxSeats);

        if (choice == 0) {
            bookedEconomyclass = ticketsToBeBooked;
        } else {
            bookedFirstclass = ticketsToBeBooked;
        }

        seat[0] = Math.max(bookedEconomyclass, bookedFirstclass);
        seat[1] = choice;
        seat[2] = 1;

        System.out.println("Booked " + className + " Seats are : " + seat[0]);
        return seat;
    }

    public static String getTravelDate(Scanner sc, String presentDate, String nextDate, DateTimeFormatter df) {
        LocalDate userDate = null;
        LocalDate startDate = LocalDate.parse(presentDate, df);
        LocalDate endDate = LocalDate.parse(nextDate, df);

        do {
            System.out.print("Enter date of travel between " + presentDate + " to " + nextDate + " [DD-MM-YYYY] : ");
            String dateInput = sc.nextLine();

            try {
                userDate = LocalDate.parse(dateInput, df);
                if (userDate.isBefore(startDate) || userDate.isAfter(endDate)) {
                    System.out.println("Date must be between " + presentDate + " and " + nextDate + ".");
                    userDate = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter a date in the format DD-MM-YYYY.");
            }
        } while (userDate == null);
        return df.format(userDate);
    }
}

class Route {
	String trafficStatus;
	int routeCode;
	String src;
	String dest;
	String departureTime;
	int fare;
	int frequency;

	public void setroute(String st, int rcode, String src, String dst, String time, int fare, int fq) {
		this.trafficStatus = st;
		this.routeCode = rcode;
		this.src = src;
		this.dest = dst;
		this.departureTime = time;
		this.fare = fare;
		this.frequency = fq;
	}

	public void getroute() {
		System.out.println("Flight No: " + routeCode);
		System.out.println("Departure: " + src);
		System.out.println("Arrival: " + dest);
		System.out.println("Departure Time: " + departureTime);
		System.out.println("Fare per seat: " + fare);
		System.out.println("Traffic in this route is: " + trafficStatus);
		System.out.println("This route is operated " + frequency + " times a week.\n");
	}
}

class Aircraft {
	String aircraftModel;
	String aircraftType;
	int pcapacity;
	int fuelcapacity;

	public void setdetails(String model, String type, int pct, int fct) {
		this.aircraftModel = model;
		this.aircraftType = type;
		this.pcapacity = pct;
		this.fuelcapacity = fct;
	}

	public void getdetails() {
		System.out.println("Aircraft Model: " + aircraftModel);
		System.out.println("Aircraft Type: " + aircraftType);
		System.out.println("Passenger Capacity: " + pcapacity);
		System.out.println("Fuel Capacity: " + fuelcapacity + " Litres\n");
	}
}

class Ticket {
	int ticketId, code;
	String flightDate;
	String seatType;
	int noSeats;

	public void setticket(int ticketId, String dot, String s, int nos, int cd) {
		this.ticketId = ticketId;
		this.flightDate = dot;
		this.seatType = s;
		this.noSeats = nos;
		this.code = cd;
	}

	public void getticket() {
		System.out.println("\nTicket Id: " + ticketId);
		System.out.println("Flight Date: " + flightDate);
		System.out.println("Seat type: " + seatType);
		System.out.println("No of seats booked: " + noSeats);
	}
}
