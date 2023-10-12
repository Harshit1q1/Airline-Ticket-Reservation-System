import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

public class AirlineTicketReservationSystem {
	public static void main(String[] args) {
		Random rand = new Random();
		Scanner sc = new Scanner(System.in);

        LocalDate dt = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String pdate = dt.format(df);

        LocalDate dt_next = dt.plusMonths(3);
        String nextdate = dt_next.format(df);

        int op = 1, cnf, pmt, id = 0, flag = 0;

        String section = new String();
        String[] city = new String[] { "Delhi", "Mumbai", "Chennai", "Kolkata" };
        String[] airporttype = new String[] { "Single", "Multihop" };
        int[] arr = new int[2];
        int[] mark = new int[2];

        System.out.println("\n---------------------------------------------------------------------");
        System.out.println("--------WELCOME TO SPICEJET AIRLINE TICKET RESERVATION CENTER--------");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("For Flight Booking of : Delhi-Mumbai-Chennai-Kolkata");
        System.out.println("Book your advance tickets between " + pdate + " to " + nextdate + " now.\n");

        Route[] flt = new Route[20];
        Aircraft[] acft = new Aircraft[5];
        Ticket[] tck = new Ticket[10];

        for (int i = 0; i < 20; i++)
            flt[i] = new Route();
        for (int i = 0; i < 5; i++)
            acft[i] = new Aircraft();
        for (int i = 0; i < 10; i++) {
            tck[i] = new Ticket();
            tck[i].ticket = 111;
        }

        acft[0].setdetails("Airbus A320-200", "passenger", 180, 730);
        acft[1].setdetails("Boeing 737-700", "passenger", 132, 510);
        acft[2].setdetails("Airbus A330-900neo", "passenger", 210, 650);
        acft[3].setdetails("Boeing 737-900", "passenger", 140, 580);
        acft[4].setdetails("Airbus A340-300F", "passenger", 126, 850);

        // mumbai-chennai = 1025km
        // mumbai-delhi = 1166km
        // mumbai-kolkata = 1664km
        // delhi-kolkata = 1304km
        // delhi-chennai = 1757km
        // kolkata-chennai = 1363km

        flt[0].setroute("moderate", 4531, "Delhi", "Mumbai", "11:00-AM", 4450, 5);
        flt[1].setroute("moderate", 1328, "Delhi", "Mumbai", "04:30-PM", 4000, 5);
        flt[2].setroute("busy", 5214, "Mumbai", "Delhi", "10:45-AM", 5100, 4);
        flt[3].setroute("busy", 4589, "Mumbai", "Delhi", "03:00-PM", 4100, 6);
        flt[4].setroute("low", 7842, "Delhi", "Chennai", "11:45-AM", 9000, 6);
        flt[5].setroute("low", 1310, "Delhi", "Chennai", "06:00-PM", 9310, 7);
        flt[6].setroute("low", 8659, "Chennai", "Delhi", "10:00-PM", 8420, 4);
        flt[7].setroute("moderate", 3162, "Delhi", "Kolkata", "10:30-AM", 5560, 5);
        flt[8].setroute("low", 7159, "Kolkata", "Delhi", "09:50-AM", 6120, 5);
        flt[9].setroute("moderate", 5214, "Mumbai", "Kolkata", "12:00-AM", 5700, 6);
        flt[10].setroute("moderate", 1159, "Mumbai", "Kolkata", "07:00-PM", 6100, 4);
        flt[11].setroute("busy", 5326, "Kolkata", "Mumbai", "02:00-PM", 5990, 7);
        flt[12].setroute("busy", 8832, "Kolkata", "Mumbai", "09:00-PM", 6150, 4);
        flt[13].setroute("busy", 5214, "Mumbai", "Chennai", "08:00-AM", 4090, 5);
        flt[14].setroute("moderate", 1453, "Mumbai", "Chennai", "10:00-PM", 5130, 5);
        flt[15].setroute("low", 6432, "Chennai", "Mumbai", "01:00-PM", 9150, 7);
        flt[16].setroute("low", 8937, "Chennai", "Mumbai", "11:35-PM", 8430, 5);
        flt[17].setroute("busy", 8659, "Chennai", "Kolkata", "10:00-AM", 4520, 6);
        flt[18].setroute("moderate", 8659, "Chennai", "Kolkata", "09:00-PM", 4130, 4);
        flt[19].setroute("low", 5326, "Kolkata", "Chennai", "02:00-PM", 5990, 6);

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
                        String date = getTravelDate(sc, pdate, nextdate, df);

                        System.out.printf("\n-----------------Available Flights :------------------\n\n");
                        for (int i = 0; i < 20; i++) {
                            if ((flt[i].src.compareTo(city[depr]) == 0) && (flt[i].dst.compareTo(city[arvl]) == 0)) {
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
                            arr = seatAvailability(sc, rand);
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
                        if (del == tck[i].ticket) {
                            System.out.println("\n----------------Your Ticket Details--------------");
                            tck[del].getticket();
                            flt[tck[del].code].getroute();
                            System.out.println("Press 1 to confirm Cancellation of Your Ticket else press 0 : ");
                            int d = sc.nextInt();
                            sc.nextLine();
                            flag = 1;
                            if (d == 1) {
                                tck[del].ticket = 111;
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
                    int temp1 = rand.nextInt(2);
                    int temp2 = rand.nextInt(2);
                    flag = 0;
                    for (int i = 0; i < 10; i++) {
                        if (find == tck[i].ticket) {
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.print("Your flight will take off from " + flt[tck[find].code].src);
                            System.out.println(" airport which is a " + airporttype[temp1] + " runway airport.");
                            System.out.print("Your flight will land on " + flt[tck[find].code].dst);
                            System.out.println(" airport which is a " + airporttype[temp2] + " runway airport.");
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
                        System.out.println("No such ticket exist of your entered Ticket Id!!");
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

	public static int[] seatAvailability(Scanner sc, Random rand) {
        int[] seat = new int[3];
        int bookedEconomyclass, bookedFirstclass;

        int totalSeats = (int) (Math.random() * (31) + 30);
        int EconomySeats = (int) (totalSeats * 0.7);
        int FirstclassSeats = totalSeats - EconomySeats;

        int freeEconomySeats = rand.nextInt(EconomySeats);
        int freeFirstclassSeats = rand.nextInt(FirstclassSeats);
        int totalfreeSeats = freeEconomySeats + freeFirstclassSeats;

        System.out.println("\n\nTotal Available Seats are : " + totalfreeSeats);
        System.out.println("Available  Economy Seats are : " + freeEconomySeats);
        System.out.println("Available First Class Seats are : " + freeFirstclassSeats);

        System.out.println("\nTo book an Economy class Ticket Enter 0 or");
        System.out.println("To book a First class Ticket Enter 1 : ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) {
            System.out.print("\n\nHow many Tickets do you want to book : ");
            int tnum1 = sc.nextInt();
            sc.nextLine();
            if (tnum1 > 0 && tnum1 <= freeEconomySeats) {
                bookedEconomyclass = tnum1;
                System.out.println("Booked Economy Seats are : " + bookedEconomyclass);
                seat[0] = bookedEconomyclass;
                seat[1] = 0;
                seat[2] = 1;
            } else {
                System.out.println("Enter Valid Number Of Tickets!!");
            }
        } else if (choice == 1) {
            System.out.print("\nHow many Tickets do you want to book : ");
            int tnum2 = sc.nextInt();
            sc.nextLine();
            if (tnum2 > 0 && tnum2 <= freeFirstclassSeats) {
                bookedFirstclass = tnum2;
                System.out.println("Booked First class Seats are : " + bookedFirstclass);
                seat[0] = bookedFirstclass;
                seat[1] = 1;
                seat[2] = 1;
            } else {
                System.out.println("Enter Valid Number Of Tickets!!");
            }
        } else {
            System.out.println("Enter Valid Choice!!");
        }

        return seat;
    }

    public static String getTravelDate(Scanner sc, String pdate, String nextdate, DateTimeFormatter df) {
        LocalDate user_date = null;
        do {
            System.out.print("Enter date of travel between " + pdate + " to " + nextdate + " [DD-MM-YYYY] : ");
            String dateInput = sc.nextLine();

            try {
                user_date = LocalDate.parse(dateInput, df);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter a date in the format DD-MM-YYYY.");
            }
        } while (user_date == null);
        return df.format(user_date);
    }
}

class Route {
	String status;
	int routecode;
	String src;
	String dst;
	String time;
	int fare;
	int frequency;

	public void setroute(String st, int rcode, String x, String y, String tm, int fr, int fq) {
		this.status = st;
		this.routecode = rcode;
		this.src = x;
		this.dst = y;
		this.time = tm;
		this.fare = fr;
		this.frequency = fq;
	}

	public void getroute() {
		System.out.println("Flight No :" + routecode);
		System.out.println("Departure :" + src);
		System.out.println("Arrival :" + dst);
		System.out.println("Departure Time :" + time);
		System.out.println("Fare per seat :" + fare);
		System.out.println("Traffic in this route is :" + status);
		System.out.println("This route is operated " + frequency + " times a week.");
		System.out.println("");
	}
}

class Aircraft {
	String modelname;
	String type;
	int pcapacity;
	int fuelcapacity;

	public void setdetails(String x, String y, int pct, int fct) {
		this.modelname = x;
		this.type = y;
		this.pcapacity = pct;
		this.fuelcapacity = fct;
	}

	public void getdetails() {
		System.out.println("Aircraft Model :" + modelname);
		System.out.println("Aircraft Type :" + type);
		System.out.println("Passenger Capacity :" + pcapacity);
		System.out.println("Fuel Capacity :" + fuelcapacity + " Litres");
		System.out.println("");
	}
}

class Ticket {
	int ticket, code;
	String dot;
	String Seat;
	int noSeats;

	public void setticket(int t, String dt, String s, int nos, int cd) {
		this.ticket = t;
		this.dot = dt;
		this.Seat = s;
		this.noSeats = nos;
		this.code = cd;
	}

	public void getticket() {
		System.out.println("\nTicket Id : " + ticket);
		System.out.println("Flight Date :" + dot);
		System.out.println("Seat type :" + Seat);
		System.out.println("No of seats booked :" + noSeats);
	}
}
