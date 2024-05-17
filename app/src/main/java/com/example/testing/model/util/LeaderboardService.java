package com.example.testing.model.util;

import android.content.Context;

import com.example.testing.model.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardService {


    public static List<Player> readHighScoreFile(String filename) {
        System.out.println("readHighScoreFile");
        List<Player> players = new ArrayList<>();
        try {

            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(
                    new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    records.add(Arrays.asList(values));
                }
            }
            records.forEach((n) -> {
                Player player = new Player();
                System.out.println(n);
                player.setName(n.get(0));
                player.setScore(Integer.parseInt(n.get(1)));
                System.out.println(player.getName());
                System.out.println(player.getScore());
                players.add(player);
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    public static void addHighScore(File file, Player player, List<Player> players) {

        System.out.println("addHighScore");

       try {

           clearFile(file);

           if (players.size()>=10 )  {
               for (Player n : players) {
                   if (player.getScore() > n.getScore()) {
                       players.remove(9);
                       players.add(player);
                       break;
                   }
               }
//               /data/user/0/com.example.testing/files/leaderboard/Easy.csv
           }
           else {
               players.add(player);


           }
           players = sortLeaderBoard(players);
//           FileWriter fw = new FileWriter(filename, true);
//           FileOutputStream fileOutputStream = new FileOutputStream();
//           System.out.println("FileWriter");
//           BufferedWriter bw = new BufferedWriter(fw);
//           System.out.println("BufferedWriter");
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
           players.forEach((n) ->  {
               try {
                   fileOutputStream.write((n.getName()+","+ n.getScore()).getBytes());
                   fileOutputStream.write(System.getProperty("line.separator").getBytes());
//                   bw.write(n.getName()+","+ n.getScore());
//                   bw.newLine();
                   System.out.println("newLine");

               } catch (IOException e) {
                   System.out.println(e);
                   throw new RuntimeException(e);
               }


           });

           fileOutputStream.flush();


       }  catch (Exception e) {
           System.out.println(e);
       }

    }

    private static void clearFile(File file) throws IOException {
        System.out.println("clearFile");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.close();

    }

    private static List<Player> sortLeaderBoard(List<Player> players) {

        System.out.println("sortLeaderBoard");
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });

        return players;
    }



    public static void createLeaderboardFile(Context mcoContext) {
        File dir = new File(mcoContext.getFilesDir(), "leaderboard");

        System.out.println("createLeaderboardFile");
        if(!dir.exists()){
            dir.mkdir();
            System.out.println("dir.mkdir()");
            try {
                File easyFile = new File(dir, "Easy.csv");
                FileWriter easywriter = new FileWriter(easyFile);
                easywriter.append("");
                easywriter.flush();
                easywriter.close();


                File mediumFile = new File(dir, "Medium.csv");
                FileWriter mediumwriter = new FileWriter(mediumFile);
                mediumwriter.append("");
                mediumwriter.flush();
                mediumwriter.close();

                File hardFile = new File(dir, "Hard.csv");
                FileWriter hardwriter = new FileWriter(hardFile);
                hardwriter.append("");
                hardwriter.flush();
                hardwriter.close();
                System.out.println("hardwriter.close()");
            } catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }

        }


    }



}
