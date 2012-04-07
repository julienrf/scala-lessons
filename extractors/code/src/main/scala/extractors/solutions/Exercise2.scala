package extractors.solutions

object Exercise2 {

  object At {

    def unapply(s: String): Option[(String, String)] = {
      val parts = s split "@"
      if (parts.length == 2) Some(parts(0), parts(1)) else None
    }

  }
}

