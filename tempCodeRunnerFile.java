        repo.add(room);

        SearchRoom search = new SearchRoom(repo, new PriceAscendingSort());

        List<Room> results = search.search(null, request, 4, 0, 1000);
        System.out.println("Results: " + results.size()); // should be 1