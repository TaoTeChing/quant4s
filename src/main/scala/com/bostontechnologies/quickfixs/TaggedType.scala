package com.bostontechnologies.quickfixs

/*
 * Copyright (c) 2011 Miles Sabin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * This is used for annotating types.
 * http://etorreborre.blogspot.com/2011/11/practical-uses-for-unboxed-tagged-types.html
 *
 * This is excerpt from:
 * https://raw.github.com/milessabin/shapeless/master/src/main/scala/shapeless/typeoperators.scala
 */
object TaggedType {

  type Tagged[U] = {type Tag = U}
  type @@[T, U] = T with Tagged[U]

  class Tagger[U] {
    def apply[T](t : T) : T @@ U = t.asInstanceOf[T @@ U]
  }

  def tag[U] = new Tagger[U]
}
