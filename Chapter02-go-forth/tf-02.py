# -*- coding: utf-8 -*- 
#!/usr/bin/env python
import sys, re, operator, string

#
# 가장 중요한 데이터 스택
#
stack = []

#
# 힙. 이름을 데이터에 매핑한다(즉, 변수)
#
heap = {}

#
# 프로그램의 새 '단어들'(프로시저)
#
def read_file():
    """
        takes a path to a file on the stack and places the entier
        contents of the file back on the stack.
    """
    f = open(stack.pop())
    # 스택에 그 결과를 넣는다.
    stack.append([f.read()])
    f.close()

def filter_chars():
    """
        Takes data on the stack and places back a copy whith all
        nonallphanumeric chars replaced by white space.
    """

    # 이 내용은 형식에 속하진 않는다. 정규 표현식은 너무 고수주이지만 빠르고 짧게
    # 처리하기 위해 사용한다. 해당 패턴을 스택에 넣는다.
    stack.append(re.compile('[\W_]+'))
    # 그 결과를 스택에 넣는다.
    stack.append([stack.pop().sub(' ', stack.pop()[0]).lower()])

def scan():
    """
        Takes a string on the stack and scans for words, placing
        the list o words back on the stack
    """
    # 다시 이야기하지만 split()은 이 형식에 너무 고수준이지만
    # 빠르고 짧게 처리하기 위해 사용한다. 연습문제로 남겨둔다.
    stack.extend(stack.pop()[0].split())

def remove_stop_words():
    """
        Takes a list of words on the stack and removes stop words.
    """
    f = open('../stop_words.txt')
    stack.append(f.read().split(','))
    f.close()
    # 한 글자로 된 단어를 추가한다.
    stack[-1].extend(list(string.ascii_lowercase))
    heap['stop_words'] = stack.pop()
    # 다시 이야기 하지만 이것은 이 형식에 너무 고수준이지만
    # 빠르고 짧게 처리하기 위해 사용한다. 연습문제로 남겨 둔다.
    heap['words'] = []
    while len(stack) > 0:
        if stack[-1] in heap['stop_words']:
            stack.pop() # 꺼낸 후 버린다.
        else:
            heap['words'].append(stack.pop()) # 꺼낸 후 저장한다.
    stack.extend(heap['words']) # 단어를 스택에 적재한다.
    del heap['stop_words']; # 불필요하다.
    del heap['words'];      # 불필요하다.

def frequencies():
    """
        Takes a list of words and returns a dictionary associating
        words with frequencies of occurrence.
    """
    heap['word_freqs'] = {}
    # 실제 '포스(Forth)' 형식의 특색이 약간 나타나는 부분은 여기부터...
    while len(stack) > 0:
        # ... 이지만 다음 줄은 형식에 속하지 않는다.
        # 기교 없이 순진한 구현 내용은 너무 느리다.
        if stack[-1] in heap['word_freqs']:
            # 빈도를 증가시키며, 다음과 같은 후위 형식이다: f 1 +
            stack.append(heap['word_freqs'][stack[-1]])     # f를 넣는다.
            stack.append(1)     # 1을 넣는다.
            stack.append(stack.pop() + stack.pop())     # 더한다.
        else:
            stack.append(1)     # stack[2]에 1을 넣는다.
        # 갱신한 빈도를 힙에 다시 적재한다.
        heap['word_freqs'][stack.pop()] = stack.pop()

    # 그 결과를 스택에 넣는다.
    stack.append(heap['word_freqs'])
    del heap['word_freqs']      # 이 변수는 더 이상 필요치 않다.

def sort():
    # 형식에 속하지 않는다. 연습문제로 남겨 둔다.
    stack.extend(sorted(stack.pop().iteritems(), key=operator.itemgetter(1)))

# 주 함수
#
stack.append(sys.argv[1])
read_file()
filter_chars()
scan()
remove_stop_words()
frequencies()
sort()

stack.append(0)
# 스택의 길이를 1을 기준으로 검사한다. 처리를 마친 후 
# 남아 있을 항목 하나는 마지막 단어가 될 것이기 때문이다.
while stack[-1] < 25 and len(stack) > 1:
    heap['i'] = stack.pop()
    (w, f) = stack.pop()
    print w, ' - ' , f
    stack.append(heap['i'])
    stack.append(1)
    stack.append(stack.pop() + stack.pop())

