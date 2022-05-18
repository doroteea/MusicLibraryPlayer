//package com.lab4.demo.comment;
//
//import com.lab4.demo.comment.model.Comment;
///**
// * Hello world!
// *
// */
//public class MongoJavaDriverSampleApp {
//
//    private static final CommentService commentDao = new CommentService();
//
//    public static void main(String[] args) {
//        // Clear comment collection by removing all entities
//
//        // Save couple of comments
//        Comment comment = new Comment();
//        comment.setContent("Ning");
//        comment.setUserId(1L);
//        comment.setTrackId(1L);
//
//      //  commentDao.save(comment);
//
////        // fetch all comments
////        System.out.println("comments found with findAll():");
////        System.out.println("-------------------------------");
////        for (comment comment : commentDao.findAll()) {
////            System.out.println(comment);
////        }
////        System.out.println();
////
////        // fetch top 2 comments by last name
////        System.out.println("comments found with findTop2ByLastName('Smith'):");
////        System.out.println("-------------------------------");
////        for (comment comment : commentDao.findTop2ByLastName("Smith")) {
////            System.out.println(comment);
////        }
////        System.out.println();
////
////        // fetch top 2 comments by first name
////        System.out.println("comments found with findTop2ByFirstName('Alice'):");
////        System.out.println("-------------------------------");
////        for (comment comment : commentDao.findTop2ByFirstName("Alice")) {
////            System.out.println(comment);
////        }
////        System.out.println();
////
////        // fetch an individual comment
////        System.out.println("comment found with findOneByFirstName('Alice'):");
////        System.out.println("--------------------------------");
////        System.out.println(commentDao.findOneByFirstName("Alice"));
////        System.out.println();
////
////        // fetch an individual comment by first and last name
////        System.out.println("comment found with findOneByFirstNameAndLastName('Alice', 'Smith'):");
////        System.out.println("--------------------------------");
////        System.out.println(commentDao.findOneByFirstNameAndLastName("Alice", "Smith"));
////        System.out.println();
////
////        System.out.println("comments found with findByFirstName('Alice'):");
////        System.out.println("--------------------------------");
////        for (comment comment : commentDao.findByFirstName("Alice")) {
////            System.out.println(comment);
////        }
////        System.out.println();
////
////        System.out.println("comments found with findByLastName('Smith'):");
////        System.out.println("--------------------------------");
////        for (comment comment : commentDao.findByLastName("Smith")) {
////            System.out.println(comment);
////        }
////        System.out.println();
////
////        System.out.println("comments countByLastName('smith'): " + commentDao.countByLastName("smith"));
////        System.out.println(
////                "comments countByLastNameIgnoreCase('smith'): " + commentDao.countByLastNameIgnoreCase("smith"));
////
////        // sample modification
////        comment commentEntity = commentDao.findOneByFirstNameAndLastName("Alice", "Smith");
////        commentEntity.setLastName("Smiths");
////        commentEntity = commentDao.save(commentEntity);
////
////        System.out.println("comments found with findByAddressCity('Warszawa'):");
////        System.out.println("--------------------------------");
////        for (comment comment : commentDao.findByAddressCity("Warszawa")) {
////            System.out.println(comment);
////        }
////        System.out.println();
////
////        System.out.println("comments found with findByAgeBetween(20, 60):");
////        System.out.println("--------------------------------");
////        for (comment comment : commentDao.findByAgeBetween(20, 60)) {
////            System.out.println(comment);
////        }
////
////        commentDao.close();
//    }
//
//}